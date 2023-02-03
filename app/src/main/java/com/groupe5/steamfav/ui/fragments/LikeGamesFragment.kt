package com.groupe5.steamfav.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.groupe5.steamfav.R
import com.groupe5.steamfav.abstraction.ItemClickListener
import com.groupe5.steamfav.data.GamesRepository
import com.groupe5.steamfav.databinding.FragmentLikeGameBinding
import com.groupe5.steamfav.network.models.LikedGame
import com.groupe5.steamfav.network.services.SteamStoreNetwork
import com.groupe5.steamfav.network.services.SteamWorksWebNetwork
import com.groupe5.steamfav.ui.activity.MainActivity
import com.groupe5.steamfav.ui.adapter.FirebaseGamesAdapter
import com.groupe5.steamfav.ui.models.GameItem
import com.groupe5.steamfav.utils.setupActionBarFromFragment
import com.groupe5.steamfav.viewmodels.HomeViewModel
import com.groupe5.steamfav.viewmodels.factory.ViewModelFactory


class LikeGamesFragment : Fragment(), ItemClickListener<LikedGame> {



    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory(
            this,
            GamesRepository(
                SteamWorksWebNetwork(),
                SteamStoreNetwork()
            )
        )
    }
    private var _binding: FragmentLikeGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var authProvider:FirebaseAuth
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
        val db=FirebaseFirestore.getInstance()
        val authProvider=Firebase.auth
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