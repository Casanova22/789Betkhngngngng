package com.ccuwu.a789betkhngngng

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ccuwu.a789betkhngngng.connection.BetConnectionHandler
import com.ccuwu.a789betkhngngng.databinding.FragmentFirstBinding
import com.ccuwu.a789betkhngngng.view.BetViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FragmentBetFirst : Fragment(), View.OnClickListener {

    private var _firstBinding : FragmentFirstBinding? = null
    private val binding get() = _firstBinding!!

    private val args = Bundle()
    private lateinit var viewModel: BetViewModel
    private var checkInternetConnection = BetConnectionHandler()
    private var checknet = false

    private var _db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        _firstBinding = FragmentFirstBinding.inflate(inflater,container,false)

        viewModel = ViewModelProvider(this)[BetViewModel::class.java]



        connectionCheck()
        return binding.root
    }

    private fun connectionCheck() {
        checknet = checkInternetConnection.connectionError(requireActivity())
        if (checknet) {
            viewModel.readFireData()
            buttonCode()
        } else {
            Toast.makeText(context, "Vui lòng kiểm tra kết nối internet của bạn và làm mới trang.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun onClickMain(){
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn2.setOnClickListener {
            val intent = Intent(context, BetGameActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun buttonCode() {
        onClickMain()
        viewModel.readFireData().observe(viewLifecycleOwner) {
            _db.collection("06June")
                .document("789BET").get()
                .addOnSuccessListener {
                    it.let {
                        if (it != null) {
                            for (i in it.id) {
                                val packName = it.getString("PackageName")
                                val url = it.getString("JumpCodeURL")
                                val status = it.getBoolean("AppStatus")
                                if(packName == context?.packageName){
                                    Log.e(ContentValues.TAG, packName.toString())
                                    when(status){
                                        true -> {
                                            binding.btn2.visibility = View.GONE
                                            binding.subText1.visibility = View.VISIBLE
                                            binding.btn2.setOnClickListener(this)
                                            binding.b1.text = it.getString("ButtonBinding1")
                                            args.putBoolean("code", true)
                                            args.putBoolean("title",true)
                                            args.putString("urlview", url)
                                        }
                                        else -> {
                                            binding.btn2.visibility = View.VISIBLE
                                            binding.subText1.visibility = View.GONE
                                            binding.b1.text = getString(R.string.b1)
                                            args.putBoolean("code", false)
                                            args.putBoolean("title",false)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            return@observe
        }
    }

    override fun onClick(v: View?) {
        when(v){
            binding.btn1 -> findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, args)
            /*binding.btn2 -> findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment, args)*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _firstBinding = null
    }
}