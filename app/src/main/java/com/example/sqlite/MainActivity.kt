package com.example.sqlite

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

lateinit var memberDatabaseHelper:MemberDatabaseHelper
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val TAG = "MainActivity";
    lateinit var context:Context

    val mainFragment: MainFragment = MainFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        memberDatabaseHelper= MemberDatabaseHelper(context)
        setContentView(R.layout.activity_main)
        titleColor=R.drawable.side_nav_bar;
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        var boolean = true
        fab.setOnClickListener { view ->
            if(boolean)
            memberDatabaseHelper.addName("Alice")
            else{
                memberDatabaseHelper.addName("Lyon")
            }
            mainFragment.onResume()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        init()
    }



    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.option_main, menu)
        return  super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                AlertDialog.Builder(context).setTitle(context.getString(R.string.action_settings)).setMessage(context.getString(R.string.action_settings)).setCancelable(true).create().show()

                true
            }
            R.id.action_remove->{
               memberDatabaseHelper.remove()
                mainFragment.onResume()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_float_view -> {
                AlertDialog.Builder(context).setTitle(context.getString(R.string.action_settings)).setMessage(context.getString(R.string.menu_FloatView)).setCancelable(true).create().show()

            }
            R.id.nav_about_me -> {
               var alert =  AlertDialog.Builder(context)
                alert.setTitle(context.getString(R.string.menu_aboutMe)).setMessage(context.getString(R.string.about_Me_sub)).setCancelable(true)
                alert.setPositiveButton(getString(R.string.contactMe),object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        shareEmail("lejiteyu@gmail.com","Android App "+getString(R.string.app_name)+"ver:"+ Tool().getVersionName(context)+"("+Tool().getVersionCode(context)+")","hello")
                    }
                })
                alert.create()
                alert.show()
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun init(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_layout, mainFragment)
        transaction.commit()
    }

    fun shareEmail(
        to_email_id: String,
        subject: String?,
        body: String?
    ) {
        // This function will open the email client installed in the device to share from your own app through intent.
        val sharingIntent = Intent(Intent.ACTION_SEND, Uri.parse(""))
        sharingIntent.type = "message/rfc822"

        /* All the below fields are optional. If not given simply opens the email client */
        // To email id
        sharingIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to_email_id))
        // Subject that needs to appear while sharing
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        // Body of the mail content shared.
        sharingIntent.putExtra(Intent.EXTRA_TEXT, body)
        startActivity(
            Intent.createChooser(sharingIntent, "Share content through email")
        )
    } // shareEmail

}


