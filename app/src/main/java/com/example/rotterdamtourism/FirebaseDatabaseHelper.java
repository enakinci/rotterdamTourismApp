package com.example.rotterdamtourism;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceHotels;
    private DatabaseReference mReferenceSights;
    private List<Hotel> hotels = new ArrayList<>();
    private List<Sights> sights = new ArrayList<>();

    public interface DataStatus {
        void DataIsLoaded(List<Hotel> hotels, List<String> keys);
        void SightsDataIsLoaded(List<Sights> sights, List<String> keys)
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance();
        mReferenceHotels = mDatabase.getReference("Hotels");
        mReferenceSights = mDatabase.getReference("Sights");
    }


    public void readSights (final DataStatus dataStatus) {
        mReferenceSights.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sights.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Sights sights = keyNode.getValue(Sights.class);
                    sights.add(sights);
                }
                dataStatus.SightsDataIsLoaded(sights, keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}


    public void readHotels (final DataStatus dataStatus) {
        mReferenceHotels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hotels.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Hotel hotel = keyNode.getValue(Hotel.class);
                    hotels.add(hotel);
                }
                dataStatus.DataIsLoaded(hotels, keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
