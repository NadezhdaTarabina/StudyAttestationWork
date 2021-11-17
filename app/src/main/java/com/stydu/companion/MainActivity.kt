package com.stydu.companion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

const val MY_NAME: String = "YOU"
private const val LAST_SELECTED_ITEM: String ="LAST_SELECTED_ITEM"
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val companionChatFragment: Fragment = CompanionChatFragment()
        val shareFragment: Fragment = ShareFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, companionChatFragment)
            .commit()

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when(item.itemId) {
                R.id.chat_bottom_navigator -> {
                    fragment = companionChatFragment
                }
                R.id.share_bottom_navigator -> {
                    fragment = shareFragment
                }
            }
            replaceFragment(fragment!!)
            true
        }

        bottomNavigationView.selectedItemId =
            savedInstanceState?.getInt(LAST_SELECTED_ITEM) ?: R.id.chat_bottom_navigator


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_SELECTED_ITEM,bottomNavigationView.selectedItemId)

    }

    private fun replaceFragment (fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .addToBackStack(null)
            .commit()
    }


}

