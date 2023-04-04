package bell.test.guidomia_challenge.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import bell.test.guidomia_challenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}