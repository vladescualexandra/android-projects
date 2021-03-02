package eu.ase.ro.seminar11.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import eu.ase.ro.seminar11.util.Coach;

public class FirebaseService {
    public static final String COACH_TABLE_NAME = "coaches";
    private DatabaseReference database;

    private static FirebaseService firebaseService;

    private FirebaseService() {
        //acces firebase prin FirebaseDatabase.getInstance
        //initializare conexiune nod referit prin getReference(COACH_TABLE_NAME)
        database = FirebaseDatabase.getInstance().getReference(COACH_TABLE_NAME);
    }

    public static FirebaseService getInstance() {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }

    public void upsert(Coach coach) {
        if (coach == null) {
            return;
        }
        //verificam daca obiectul coach nu are id
        if (coach.getId() == null || coach.getId().trim().isEmpty()) {
            //adaugam o cheie noua in firebase, mare atentie aceasta nu contine si valorile obiectului coach
            String id = database.push().getKey();
            coach.setId(id);
        }
        //ne pozitionam pe un copil din coaches (cel pe care l-am adaugat la linia 48 daca coach.getId == null,
        //altfel pe cel pe care l-am primit in obiectul coach)
        //setValue asigura adaugarea/suprascrierea informatiilor stocate in copilul pozitionat
        database.child(coach.getId()).setValue(coach);
    }

    public void delete(final Coach coach) {
        if (coach == null || coach.getId() == null || coach.getId().trim().isEmpty()) {
            return;
        }
        //ne pozitionam pe un copil din coaches (cel pe care l-am primit in obiectul coach)
        //removeValue asigura stergerea informatiilor stocate in copilul pozitionat
        database.child(coach.getId()).removeValue();
    }

    public void attachDataChangeEventListener(final Callback<List<Coach>> callback) {
        //evenimentul este atasat la nivel de coaches (reference) prin urmare asculta orice modificare
        // de insert/update/delete executata asupra acestui nod
        //apelul metodelor upsert si delete de mai sus forteaza primirea unei notificari de la Firebase in acest eveniment
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Coach> coaches = new ArrayList<>();
                //parcurgem lista de subnoduri din cel principal
                for (DataSnapshot data : snapshot.getChildren()) {
                    //folosim reflection pentru convertirea de la Snapshot la Coach
                    //mare atentie sa definim in Coach un constructor default + getteri si setteri
                    Coach coach = data.getValue(Coach.class);
                    if (coach != null) {
                        coaches.add(coach);
                    }
                }
                //trimitem lista catre activitatea prin intermediul callback-ului
                callback.runResultOnUiThread(coaches);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FirebaseService", "Data is not available");
            }
        });
    }
}
