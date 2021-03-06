package example.bedilbek.contactapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ContactListFragment.OnContactSelectedListener {
    FragmentManager manager;
    LinearLayout parent;
    LinearLayout menu;
    LinearLayout content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        manager = getSupportFragmentManager();
        ArrayList<Contact> contacts = initContacts();
        ContactListFragment fragment = ContactListFragment.constructor(contacts);
        fragment.contactSelectedListener = this;
        FragmentTransaction transaction = manager.beginTransaction();
        configure(getResources().getConfiguration().orientation);
        if (transaction.isEmpty())
            transaction.add(R.id.contact_list_layout, fragment);
        else {
            transaction.replace(R.id.contact_list_layout, fragment);
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    private void init() {
        parent = findViewById(R.id.parent);
        menu = findViewById(R.id.contact_list_layout);
        content = findViewById(R.id.contact_detail_layout);
    }

    private void configure(int ORIENTATION) {
        if (ORIENTATION == Configuration.ORIENTATION_PORTRAIT) {
            parent.setWeightSum(1);
            LinearLayout.LayoutParams menuParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
            menu.setLayoutParams(menuParams);
            LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            content.setLayoutParams(contentParams);
        } else {
            parent.setWeightSum(2);
            LinearLayout.LayoutParams menuParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            menu.setLayoutParams(menuParams);
            LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            content.setLayoutParams(contentParams);
        }
    }

    private ArrayList<Contact> initContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("131", "superman", "Bedilbek", "Khamidov", 72318823));
        contacts.add(new Contact("124", "batman", "Azizbek", "Matchanov", 52315823));
        contacts.add(new Contact("130", "spiderman", "Peter", "Parker", 44367190));
        contacts.add(new Contact("123", "catwoman", "Selina", "Kyle", 12312223));
        contacts.add(new Contact("125", "cyborg", "Victor", "Stone", 52367823));
        contacts.add(new Contact("126", "flash", "Bart", "Allen", 45367113));
        contacts.add(new Contact("127", "greenarrow", "Oliver", "Queen", 92672223));
        contacts.add(new Contact("128", "hawkgirl", "Kendra", "Sanders", 52315823));
        contacts.add(new Contact("129", "isis", "Andrea", "Thomas", 82377823));
        contacts.add(new Contact("132", "zatannawoman", "Zatanna", "Unknown", 34377823));
        contacts.add(new Contact("133", "wonderwoman", "Diana", "Princess", 46368790));
        return contacts;
    }

    @Override
    public void onObjectSelected(Contact contact) {
        ContactDetailsFragment detailsFragment = ContactDetailsFragment.constructor(contact);
        FragmentTransaction transaction = manager.beginTransaction();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            configure(getResources().getConfiguration().orientation);
            transaction.replace(R.id.contact_detail_layout, detailsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            configure(getResources().getConfiguration().orientation);
            Intent intent = new Intent(this, ContactDetailsActivity.class);
            intent.putExtra("contact", (Parcelable) contact);
            startActivity(intent);
//            transaction.replace(R.id.contact_list_layout, detailsFragment);
        }
//        transaction.addToBackStack(null);
//        transaction.commit();
    }
}
