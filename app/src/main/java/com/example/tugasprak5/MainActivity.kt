package com.example.tugasprak5

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugasprak5.ui.theme.TugasPrak5Theme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TugasPrak5Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BiodataScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiodataScreen() {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }

    var isAndroidChecked by remember { mutableStateOf(false) }
    var isUiUxChecked by remember { mutableStateOf(false) }
    var isWebChecked by remember { mutableStateOf(false) }

    // State untuk Dropdown Experience
    var expandedExperience by remember { mutableStateOf(false) }
    val experienceOptions = listOf("Beginner", "Intermediate", "Expert")

    // Konteks untuk DatePicker & Toast
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            dob = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim)
    ) {
        // 1. Header Gambar Motor
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.motor_header),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // 2. Card Form Mengapung
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            Card(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.form_title),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.purple_primary)
                    )
                    Text(
                        text = stringResource(id = R.string.form_subtitle),
                        fontSize = 12.sp,
                        color = colorResource(id = R.color.text_secondary),
                        modifier = Modifier.padding(top = 4.dp, bottom = 20.dp)
                    )

                    // Input Nama Depan & Nama Belakang
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            placeholder = { Text(stringResource(id = R.string.hint_first_name)) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = lastName,
                            onValueChange = { lastName = it },
                            placeholder = { Text(stringResource(id = R.string.hint_last_name)) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Input Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text(stringResource(id = R.string.hint_email)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Input Tgl Lahir & Experience (Dropdown)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Tgl Lahir dengan DatePicker Dialog
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = dob,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text(stringResource(id = R.string.hint_dob)) },
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = android.R.drawable.ic_menu_my_calendar),
                                        contentDescription = null
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { datePickerDialog.show() },
                                enabled = false,
                                colors = OutlinedTextFieldDefaults.colors(
                                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    disabledBorderColor = MaterialTheme.colorScheme.outline
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .clickable { datePickerDialog.show() }
                            )
                        }

                        // Experience Dropdown Menu
                        ExposedDropdownMenuBox(
                            expanded = expandedExperience,
                            onExpandedChange = { expandedExperience = !expandedExperience },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = experience,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text(stringResource(id = R.string.hint_experience)) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedExperience)
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = expandedExperience,
                                onDismissRequest = { expandedExperience = false }
                            ) {
                                experienceOptions.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption) },
                                        onClick = {
                                            experience = selectionOption
                                            expandedExperience = false
                                        }
                                    )
                                }
                            }
                        }
                    }