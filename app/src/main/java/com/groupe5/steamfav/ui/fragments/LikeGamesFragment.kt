package com.groupe5.steamfav.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.groupe5.steamfav.R
import com.groupe5.steamfav.abstraction.ItemClickListener
import com.groupe5.steamfav.databinding.FragmentLikeGameBinding
import com.groupe5.steamfav.network.models.LikedGame
import com.groupe5.steamfav.ui.adapter.FirebaseGamesAdapter
import com.groupe5.steamfav.utils.setupActionBarFromFragment
import org.koin.android.ext.android.inject

class LikeGamesFragment : Fragment(), ItemClickListener<LikedGame> {


    private var _binding: FragmentLikeGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val authProvider:FirebaseAuth by inject()
    private val db:FirebaseFirestore by inject()
    private val  naveController by lazy {
        findNavController()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikeGameBinding.inflate(inflater, container, false)
        appBarConfiguration = AppBarConfiguration(setOf(R.navigation.main))
        (requireActivity() as AppCompatActivity).setupActionBarFromFragment(binding.toolbar,findNavController(),appBarConfiguration)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gamesRecyclerView = binding.likedGameList
        val currentUser=authProvider.currentUser
        if(currentUser ==null) {
            naveController.navigate(R.id.login_fragment)
        }
        val query:Query=db.collection("likedGames")
            .whereArrayContains("users",currentUser?.uid?:"")
            .orderBy("name")
            .limit(50)

        val options= FirestoreRecyclerOptions.Builder<LikedGame>()
            .setQuery(query,LikedGame::class.java)
            .setLifecycleOwner(viewLifecycleOwner)
            .build()
        val adapter =object:FirebaseGamesAdapter(this, emptyList(),options)  {
            override fun onDataChanged() {
                super.onDataChanged()
                val snapshots=snapshots
                binding.loader.visibility=View.GONE
                if(snapshots.isEmpty()){
                    binding.likedGameList.visibility=View.GONE
                    binding.emptyLikedGamesLayout.root.visibility=View.VISIBLE
                }else{
                    binding.likedGameList.visibility=View.VISIBLE
                    binding.emptyLikedGamesLayout.root.visibility=View.GONE
                }

            }
        }
        gamesRecyclerView.adapter = adapter
        gamesRecyclerView.setItemViewCacheSize(50)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: LikedGame) {
        val action = LikeGamesFragmentDirections.actionLikedGamesFragmentToGameDetailsDest(item.appid)
        findNavController().navigate(action)
    }



}