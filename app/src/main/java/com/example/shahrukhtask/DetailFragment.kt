package com.example.shahrukhtask

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.shahrukhtask.databinding.FragmentDetailsBinding
import com.example.shahrukhtask.githubuser.GithubRepository
import com.example.shahrukhtask.githubuser.GithubUserViewModel
import com.example.shahrukhtask.model.User
import com.example.shahrukhtask.network.GithubFactory
import com.example.shahrukhtask.network.NetworkResult
import java.util.Objects

class DetailFragment:Fragment() {
    private val githubViewModel: GithubUserViewModel by activityViewModels {
        GithubFactory(GithubRepository())
    }
    private lateinit var userentered:TextWatcher
    private lateinit var binding: FragmentDetailsBinding
    private val item by lazy {
        arguments?.getParcelable<User>("user")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        setUser()
        binding.userEnterText.addTextChangedListener(userentered)
        return binding.root
    }

    fun setUser() {
        binding.userName.text = item?.firstName
        githubViewModel.userlist.observe(viewLifecycleOwner, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    Log.d("TAG", "observe: github user Detail ${it.data} ")
                }
                is NetworkResult.Error -> {
                    Log.d("TAG", "observe: github user ${it.message} ")
                }
            }
        })

         userentered = object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: $p0")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged:$p0 ")
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("TAG", "afterTextChanged: $p0")
            }

        }
    }
}