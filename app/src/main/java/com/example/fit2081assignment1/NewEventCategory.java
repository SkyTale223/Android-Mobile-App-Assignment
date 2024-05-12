package com.example.fit2081assignment1;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class NewEventCategory extends AppCompatActivity {

    // Declaring variables
    EditText etCategoryName;
    EditText etEventCount;
    Switch swIsActive;
    private EMAViewmodel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);

        // Finding the views
        etCategoryName = findViewById(R.id.editTextCategoryName);
        etEventCount = findViewById(R.id.editTextEventCount);
        swIsActive = findViewById(R.id.switchIsActiveCategory);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(EMAViewModel.class);
    }

    public void onSave(View view) {
        String categoryName = etCategoryName.getText().toString();
        String eventCountStr = etEventCount.getText().toString();
        boolean isActive = swIsActive.isChecked();

        if (categoryName.isEmpty() || eventCountStr.isEmpty()) {
            // Show error message if any field is empty
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int eventCount = Integer.parseInt(eventCountStr);
        if (eventCount <= 0) {
            // Show error message for invalid event count
            Toast.makeText(this, "Event count must be greater than zero", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new EventCategory object
        EventCategory newEventCategory = new EventCategory(
                null, // Room database will generate the ID automatically
                categoryName,
                eventCount,
                isActive
        );

        // Insert new EventCategory into database
        viewModel.insert(newEventCategory);

        // Show success message
        Toast.makeText(this, "Category saved successfully", Toast.LENGTH_SHORT).show();

        // Finish activity
        finish();
    }
}
