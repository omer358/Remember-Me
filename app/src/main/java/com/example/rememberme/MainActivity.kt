package com.example.rememberme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    @Inject
//    lateinit var peopleDao: PeopleDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()

//        lifecycleScope.launch {
//            withContext(Dispatchers.IO){
//                peopleDao.insertAll(FakeDataSource.getPeopleList())
//            }
//        }
        setContent {
            RememberMeApp()
        }
    }
}