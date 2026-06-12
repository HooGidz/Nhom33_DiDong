package com.example.nhom33.controller;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom33.Database.FoodDB;
import com.example.nhom33.DataEntity.CategoriesEntity;
import com.example.nhom33.DataEntity.FoodsEntity;
import com.example.nhom33.R;

import java.util.ArrayList;
import java.util.List;

public class Admin_Add_Food extends AppCompatActivity {

    private EditText edtFoodName, edtDescription, edtPrice, edtPriceSale, edtImageUrl, edtMealType;
    private Spinner spinnerCategory;
    private CheckBox chkIsAvailable;
    private Button btnAddFood;
    private ImageButton btnBack;

    private List<CategoriesEntity> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_food);

        initViews();
        loadCategories();

        btnBack.setOnClickListener(v -> finish());

        btnAddFood.setOnClickListener(v -> {
            addFoods();
        });
    }

    private void initViews() {
        edtFoodName = findViewById(R.id.edt_food_name);
        spinnerCategory = findViewById(R.id.spinner_category_id);
        edtDescription = findViewById(R.id.edt_description);
        edtPrice = findViewById(R.id.edt_price);
        edtPriceSale = findViewById(R.id.edt_price_sale);
        edtImageUrl = findViewById(R.id.edt_image_url);
        edtMealType = findViewById(R.id.edt_meal_type);
        chkIsAvailable = findViewById(R.id.chk_is_available);
        btnAddFood = findViewById(R.id.btn_add_food);
        btnBack = findViewById(R.id.btn_back);
    }

    private void loadCategories() {
        // Lấy danh sách danh mục từ Database
        categoryList = FoodDB.getInstance(this).categoriesDAO().getAllCategories();
        List<String> categoryNames = new ArrayList<>();

        if (categoryList == null || categoryList.isEmpty()) {
            categoryNames.add("Chưa có danh mục nào");
        } else {
            for (CategoriesEntity category : categoryList) {
                categoryNames.add(category.getCategoryName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void addFoods() {
        // Kiểm tra xem có danh mục nào không
        if (categoryList == null || categoryList.isEmpty()) {
            Toast.makeText(this, "Vui lòng thêm danh mục trước!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy dữ liệu từ giao diện
        String name = edtFoodName.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String priceStr = edtPrice.getText().toString().trim();
        String priceSaleStr = edtPriceSale.getText().toString().trim();
        String imageUrl = edtImageUrl.getText().toString().trim();
        String mealType = edtMealType.getText().toString().trim();
        int isAvailable = chkIsAvailable.isChecked() ? 1 : 0;

        // Kiểm tra dữ liệu bắt buộc
        if (TextUtils.isEmpty(name)) {
            edtFoodName.setError("Vui lòng nhập tên món ăn");
            return;
        }
        if (TextUtils.isEmpty(priceStr)) {
            edtPrice.setError("Vui lòng nhập giá");
            return;
        }

        try {
            // Lấy Category ID từ Spinner
            int selectedPosition = spinnerCategory.getSelectedItemPosition();
            int categoryId = categoryList.get(selectedPosition).getCategoryId();

            double price = Double.parseDouble(priceStr);
            Double priceSale = null;
            if (!TextUtils.isEmpty(priceSaleStr)) {
                priceSale = Double.parseDouble(priceSaleStr);
            }

            // Khởi tạo đối tượng Entity
            FoodsEntity food = new FoodsEntity(
                    categoryId,
                    name,
                    description,
                    price,
                    priceSale,
                    imageUrl,
                    isAvailable,
                    mealType
            );

            // Thực hiện thêm vào Room Database
            FoodDB.getInstance(this).foodsDAO().insertFood(food);

            Toast.makeText(this, "Thêm món ăn thành công!", Toast.LENGTH_SHORT).show();
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Vui lòng nhập đúng định dạng số cho Giá", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Có lỗi xảy ra: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
