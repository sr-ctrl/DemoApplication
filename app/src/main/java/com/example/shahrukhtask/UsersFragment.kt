package com.example.shahrukhtask

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shahrukhtask.adapter.NewAdapter
import com.example.shahrukhtask.adapter.UserAdapter
import com.example.shahrukhtask.databinding.FragmentUsersBinding
import com.example.shahrukhtask.githubuser.GithubRepository
import com.example.shahrukhtask.githubuser.GithubUserViewModel
import com.example.shahrukhtask.network.*
import com.example.shahrukhtask.room.UserDataBase
import com.example.shahrukhtask.utils.Extensions.showSnackBar
import com.example.shahrukhtask.utils.InternetConnectionListener
import com.example.shahrukhtask.utils.InternetReceiverBroadcast
import com.google.android.material.snackbar.Snackbar

class UsersFragment : Fragment(), InternetConnectionListener, NewAdapter.OnClickOfItem {

    lateinit var viewModel: UserViewModel
    private lateinit var binding: FragmentUsersBinding
    private lateinit var adapter: NewAdapter
    private lateinit var internetConnectionReceiver: InternetReceiverBroadcast
    private val githubViewModel: GithubUserViewModel by activityViewModels() {
       GithubFactory(GithubRepository())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        internetConnectionReceiver = InternetReceiverBroadcast()
        internetConnectionReceiver = InternetReceiverBroadcast().apply {
            listener = this@UsersFragment
        }

        initializeViewModel()
        observe()
        return binding.root
    }
    private fun initializeViewModel() {
        val dao = UserDataBase.getInstance(requireActivity().application).userDao
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        apiCall()
        githubViewModel.getUsers()
    }
    private fun apiCall() {
        viewModel.getAllUsers()
    }


    private fun observe(){
        githubViewModel.userlist.observe(viewLifecycleOwner, Observer {
            when(it){
                is NetworkResult.Success -> {
                    Log.d("TAG", "observe: github user ${it.data} ")
                }
                is NetworkResult.Error -> {
                    Log.d("TAG", "observe: github user ${it.message} ")
                }
            }
        })

        viewModel.isInternetConnected.observe(requireActivity(), Observer {
            if (it){
                viewModel.getUsers("1","12")
                Toast.makeText(requireActivity(),"Internet",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(),"No internet",Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.usersResponse.observe(requireActivity(), Observer {
            when(it){
                is NetworkResult.Success -> {
                    viewModel.insertUserData()
                    viewModel.getAllUsers()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireActivity(),"${it.message}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireActivity(),"Something Went Wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.databaseUserList.observe(requireActivity(), Observer {
            Log.d("TAG", "observe: userresponse observer in recyclerview$it")
            adapter = NewAdapter(this,it)
            binding.rvUsers.layoutManager =
                LinearLayoutManager(requireActivity())
            binding.rvUsers.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }


    override fun onStart() {
        super.onStart()
        if (isOnline(requireContext())){
            viewModel.getUsers("1","12")
        }else{
            binding.rvUsers.showSnackBar("No internet")
        }
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        requireActivity().registerReceiver(internetConnectionReceiver,filter)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(internetConnectionReceiver)
    }


    override fun onInternetConnectionChanged(isConnected: Boolean) {
        viewModel.isInternetConnected.value = isConnected
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

    override fun onClickOfItem(position: Int) {
        Log.d("TAG", "onClickOfItem: ")
        val bundle = Bundle()
        bundle.putParcelable("user",viewModel.databaseUserList.value?.get(position))
        findNavController().navigate(R.id.action_usersFragment_to_detailFragment,bundle)
//        parentFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_map,DetailFragment::class.java,bundle)
//            .addToBackStack(DetailFragment::class.java.name)
//            .commit()
    }
}
//akshit ahemdabad ios fresher
// bhavna baner ios android
//primala

