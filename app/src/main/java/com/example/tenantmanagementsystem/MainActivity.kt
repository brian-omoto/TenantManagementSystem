package com.example.tenantmanagementsystem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tenantmanagementsystem.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastTenant: Tenant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CHALLENGE 2: Welcome the user
        
        val userEmail = intent.getStringExtra("USER_EMAIL")
        if (userEmail != null) {
            Toast.makeText(this, "Logged in as $userEmail", Toast.LENGTH_LONG).show()
        }

        
        // SAVE Button Logic (Includes CHALLENGE 3: Validation)
        
        binding.saveButton.setOnClickListener {
            val name = binding.tenantNameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val rent = binding.rentEditText.text.toString()

            // CHALLENGE 3: Validate the tenant
            if (name.isEmpty()) {
                binding.tenantNameEditText.error = "Required"
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                binding.phoneEditText.error = "Required"
                return@setOnClickListener
            }
            if (rent.isEmpty()) {
                binding.rentEditText.error = "Required"
                return@setOnClickListener
            }

            // If we pass validation, save the tenant
            val tenant = Tenant(name, phone, rent)
            binding.tenant = tenant
            lastTenant = tenant
        }

        
        // CALL Button Logic (Implicit Intent)
        
        binding.callButton.setOnClickListener {
            val tenant = lastTenant
            if (tenant == null) {
                Toast.makeText(this, "Save a tenant first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${tenant.phone}"))
            startActivity(intent)
        }

        
        // CHALLENGE 1: SHARE Button Logic (Implicit Intent)
        
        binding.shareButton.setOnClickListener {
            val tenant = lastTenant
            if (tenant == null) {
                Toast.makeText(this, "Save a tenant first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, tenant.summary())
            startActivity(Intent.createChooser(intent, "Share tenant"))
        }
    }
}