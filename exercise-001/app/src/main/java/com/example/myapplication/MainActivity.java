package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.example.myapplication.util.Student;
import com.example.myapplication.util.StudentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Sa se creeze o aplicatie mobila ce contine o singura activitate care
 * este formata dintr-un ListView prin intermediul caruia se afiseaza
 * o lista de studenti, unde un student este reprezentat de urmatoarele
 * atribute: nume, grupa, serie, an si facultate.
 * Sa se implementeze urmatoarele cerinte:
 * 1. Sa se creeze un proiect Android nou cu o singura activitate
 * 2. Sa se populeze layout-ul activitatii create cu un ListView
 * 3. Sa se creeze clasa Student avand atributele specificate mai sus.
 * 4. Pentru fiecare atribut al clasei Student sa se aleaga tipul de date corespunzator.
 * 5. Sa se defineasca un adapter personalizat pentru ListView, avand urmatoarele aspecte:
 * a. Un fisier xml in res/layout care sa reprezinte aspectul vizual al unei inregistrari
 * b. Fiecare atribut al clasei Student este afisat in layout intr-o componenta vizuala
 * c. Toate atributele sunt afisate in fisierul xml de la punctul a.
 * d. Fisierul xml creat pentru adapter trebuie:
 * i. Sa fie format din cel putin 3 controale vizuale diferite (de ex: 2 textview-uri sunt
 * considerate un singur control vizual)
 * ii. Sa aiba o culoare de background
 * iii. Sa contina componente vizuale care nu permit editarea informatiilor afisate.
 * 6. Sa utilizeze adapterul personalizat pentru ListView create la punctul 2.
 * 7. Sa se defineasca in memory (in metoda onCreate din activitate) o colectie de cel putin 5 obiecte care sa
 * fie afisata in ListView.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Student student1 = new Student("Student 1", 1088, "E", 3, Student.FACULTATE_CSIE);
        Student student2 = new Student("Student 2", 1075, "C", 3, Student.FACULTATE_CIG);
        Student student3 = new Student("Student 3", 1080, "D", 3, Student.FACULTATE_MRK);
        Student student4 = new Student("Student 4", 1085, "E", 3, Student.FACULTATE_CSIE);
        Student student5 = new Student("Student 5", 1088, "E", 3, Student.FACULTATE_CSIE);

        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        studentList.add(student5);

        ListView lv_students = findViewById(R.id.lv_students);
        StudentAdapter adapter = new StudentAdapter(
                getApplicationContext(),
                R.layout.main_row_item,
                studentList,
                getLayoutInflater());
        lv_students.setAdapter(adapter);
    }
}