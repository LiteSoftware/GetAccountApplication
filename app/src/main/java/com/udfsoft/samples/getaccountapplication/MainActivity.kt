/*
 *    Copyright 2024 javavirys
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    More details: https://udfsoft.com/
 */

package com.udfsoft.samples.getaccountapplication

import android.accounts.AccountManager
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.common.AccountPicker

class MainActivity : AppCompatActivity() {

    private val getAccount = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        println("Result: $result")
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val email = data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
            val password = data?.getStringExtra(AccountManager.KEY_PASSWORD)
            println(data?.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE))
            println(email)
            findViewById<TextView>(R.id.emailTextView).text = "Email: $email ($password)"
        } else {
            findViewById<TextView>(R.id.emailTextView).text = "Email: Not selected!"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pickUserAccount()
    }

    private fun pickUserAccount() {
        val intent = AccountPicker.newChooseAccountIntent(
            AccountPicker.AccountChooserOptions
                .Builder()
                .build()
        )

        getAccount.launch(intent)
    }
}