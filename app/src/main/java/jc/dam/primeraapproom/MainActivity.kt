package jc.dam.primeraapproom

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import jc.dam.primeraapproom.model.AppDatabase
import jc.dam.primeraapproom.model.User
import jc.dam.primeraapproom.ui.theme.PrimeraAppRoomTheme
import kotlin.jvm.java


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).allowMainThreadQueries()
            .build()

        val userDao = db.userDao()
        // You can now use userDao to interact with the database

        Log.d("soyGemini", "Inserting users into the database")
        userDao.insertAll(User(3, "Palomo", "Bernardino"))

        //comentario de prueba para realizar un commit con copilot

        val users: List<User> = userDao.getAll()
        Log.d("soyGemini", "Users in database: $users")

        setContent {
            PrimeraAppRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrimeraAppRoomTheme {
        Greeting("Android")
    }
}