package com.example.myapplicationtv

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.feature.navigation.NavGraph
import com.example.feature.presentation.theme.SampleTVAppTheme
import com.example.feature.presentation.viewmodel.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private var viewModel: VideoViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SampleTVAppTheme {
                viewModel = hiltViewModel()
                viewModel?.let {
                    NavGraph(rememberNavController(), it)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel?.updateTheme()
        Log.d("AppLifecycle", "App is in the foreground")
    }
}