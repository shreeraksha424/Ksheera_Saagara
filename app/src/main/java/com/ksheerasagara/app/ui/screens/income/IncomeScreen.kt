package com.ksheerasagara.app.ui.screens.income

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeScreen(
    viewModel: IncomeViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    var milkLiters by remember { mutableStateOf("") }
    var fatPercent by remember { mutableStateOf("") }
    var snfPercent by remember { mutableStateOf("") }
    var paymentAmount by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Milk Income") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = milkLiters,
                onValueChange = { milkLiters = it },
                label = { Text("Milk (Liters)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = fatPercent,
                onValueChange = { fatPercent = it },
                label = { Text("Fat %") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = snfPercent,
                onValueChange = { snfPercent = it },
                label = { Text("SNF %") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = paymentAmount,
                onValueChange = { paymentAmount = it },
                label = { Text("Payment Amount (₹)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (milkLiters.isNotBlank() && paymentAmount.isNotBlank()) {
                        viewModel.insertIncome(
                            date = Calendar.getInstance().timeInMillis,
                            cowId = null, // Simplified for now
                            milkLiters = milkLiters.toDoubleOrNull() ?: 0.0,
                            fatPercent = fatPercent.toDoubleOrNull() ?: 0.0,
                            snfPercent = snfPercent.toDoubleOrNull() ?: 0.0,
                            paymentAmount = paymentAmount.toDoubleOrNull() ?: 0.0,
                            notes = notes
                        )
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Income")
            }
        }
    }
}
