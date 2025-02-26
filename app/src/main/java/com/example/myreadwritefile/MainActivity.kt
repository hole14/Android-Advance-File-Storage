package com.example.myreadwritefile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myreadwritefile.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.butonNew.setOnClickListener(this)
        binding.butonOpen.setOnClickListener(this)
        binding.butonSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id){
            R.id.buton_new -> newFile()
            R.id.buton_open -> showFile()
            R.id.buton_save -> saveFile()

        }
    }

    private fun saveFile(){
        when{
            binding.editTitle.text.toString().isEmpty() ->
                Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            binding.editFile.text.toString().isEmpty() ->
                Toast.makeText(this, "Konten harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            else -> {
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()
                val fileModel= FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving ${fileModel.filename} file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showFile() {
        val items = fileList().filter {filName ->(filName != "profileInstalled")}.toTypedArray()
        val buider = AlertDialog.Builder(this)
        buider.setTitle("Pilih file yang diinginkan")
        buider.setItems(items) { dialog, item -> loadData(items[item].toString()) }
        val alert = buider.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.editTitle.setText(fileModel.filename)
        binding.editFile.setText(fileModel.data)
        Toast.makeText(this, "Loading ${fileModel.filename} data", Toast.LENGTH_SHORT).show()
    }

    private fun newFile() {
        binding.editTitle.setText("")
        binding.editFile.setText("")
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }
}