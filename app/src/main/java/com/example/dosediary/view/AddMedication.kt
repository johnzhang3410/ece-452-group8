package com.example.dosediary.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date

data class Medication(
    val medicationName: String,
    val startDate: Date,
    val endDate: Date,
    val refillDays: Number,
    val owner: String
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AddMedicationMain(){
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Add Medication") })
        },
        modifier = Modifier.statusBarsPadding()
    ) {
        Column(modifier = Modifier
            .padding(top = 80.dp)
            .padding(horizontal = 10.dp)
        ){
            Text(text = "Medication Name", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))
            AddName()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Start/End Dates")
            Spacer(modifier = Modifier.height(16.dp))
            AddMedDuration()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Intake Frequency")
            Spacer(modifier = Modifier.height(16.dp))
            AddMedFrequency()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Refill Days")
            Spacer(modifier = Modifier.height(16.dp))
            AddRefillDays()
            Spacer(modifier = Modifier.height(16.dp))
            SaveDeleteRow()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    //selectedChoice: MutableState<String>,
    optionItems: List<String>,
    label: String
){
    var isExpanded by remember { mutableStateOf(false) }
    var selected by remember{ mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
    ){
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {isExpanded = !isExpanded}) {
            TextField(
                modifier = Modifier.menuAnchor(),
                label = {Text(label)},
                //value = selectedChoice.value,
                value = selected,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false}) {
                optionItems.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = {Text(text = text) },
                        onClick = {
                            selected = optionItems[index]
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
            //Text("Selected option is: " + selected)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddName(){
    var nameState by remember { mutableStateOf("")}
 Box {
        OutlinedTextField(value = nameState, onValueChange = {nameState = it}, label= {Text("Name")} )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedDuration(){
    var startDateState by remember { mutableStateOf("") }
    var endDateState by remember { mutableStateOf("") }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            OutlinedTextField(value = startDateState,
                onValueChange = {startDateState = it},
                label = {Text("Start Date")},
                modifier = Modifier.weight(1f))
            OutlinedTextField(value = endDateState,
                onValueChange = {endDateState = it},
                label = {Text("End Date")},
                modifier = Modifier.weight(1f))
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedFrequency(){
    val frequencyNum = remember { mutableListOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9") }
    val frequncyRate = remember{ mutableListOf("Hourly", "Daily", "Weekly", "Monthly", "Yearly") }
    var freqNum by remember {
        mutableStateOf(frequencyNum[0])
    }
    var freqRate by remember {
        mutableStateOf(frequncyRate[0])
    }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ){
                DropDownMenu(
                    //selectedChoice = frequencyNum[0],
                    optionItems = frequencyNum,
                    label = "Times")
            }
            Text(text = "Per")
            Column(
                modifier = Modifier.weight(1f)
            ) {
                DropDownMenu(
                    //selectedChoice = freqRate,
                    optionItems = frequncyRate,
                    label = "Frequency")
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRefillDays(){
    var sliderPosition by remember {mutableStateOf(0)}
    Box(modifier = Modifier) {
        Slider(
            value = sliderPosition.toFloat(),
            onValueChange = {newValue ->
                sliderPosition = newValue.toInt()
                            },
            valueRange=0f..100f,
            steps = 100
        )
    }
    Text(text = sliderPosition.toString(), )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveDeleteRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Button(
            onClick = {null},
            modifier = Modifier.weight(1f)
        ){
            Text("Save")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = { null },
            modifier = Modifier.weight(1f)
        ) {
            Text("Delete")
        }
    }
}

