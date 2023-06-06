package com.example.navigationdrawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.navigationdrawer.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnLocalidadSeleccionadaListener{
    /*
     private static final String BASE_URL = "https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=MAD&destinationLocationCode=SCQ&departureDate=2023-05-02&adults=1&nonStop=false&max=250";
    private static final String API_KEY = "tXB57WUBAHvNqeQ0aAUOeL2cRDgfYhEB";
     */
    private AppBarConfiguration mAppBarConfiguration;
    private boolean pulsadoDosVecesAtrasParaSalir = false;
    private SharedPreferences preferencias;
    private String localidad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        /* Barra de acción */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /* Botón flotante */
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        DrawerLayout drawer = findViewById(R.id.drawer_layout); // Contenedor principal que incluye toda la interfaz con el menú deslizante
        NavigationView navigationView = findViewById(R.id.nav_view); // Menú deslizante

        // IDs fragments(mobile_navigation.xml) y eb menu deslizante(activity_main_drawer.xml)
        // a tener en cuenta para visualizar en la barra de acción
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.fragmento2, R.id.fragmento3)
                .setOpenableLayout(drawer)
                .build();

        /* Enlazamos cargador de fragments del menú deslizante (NavController) con barra de acción */
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        preferencias = getSharedPreferences("preferencias.xml", MODE_PRIVATE);
        localidad = preferencias.getString("localidad", "");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                NavigationView navigationView = findViewById(R.id.nav_view);
                // Buscamos id menú/fragmento Actual
                long idMenuPrevioSeleccionado = -1;
                for (int i = 0; i < navigationView.getMenu().size(); i++) {
                    if (navigationView.getMenu().getItem(i).isChecked()) {
                        idMenuPrevioSeleccionado = navigationView.getMenu().getItem(i).getItemId();

                    }
                }

                if (menuItem.getItemId() == R.id.nav_salir) {
                    finish();
                } else {
                    Bundle argumentos = null;
                    // Ejemplo para enviar argumentos a un fragmento
                    if (idMenuPrevioSeleccionado == R.id.nav_home && menuItem.getItemId() == R.id.fragmento2) {
                        argumentos = new Bundle();
                        argumentos.putString("dato", "X-100");
                    }


                    NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
                    NavOptions.Builder opcionesBuilder = new NavOptions.Builder();
                    opcionesBuilder.setPopUpTo(menuItem.getItemId(), true);
                    navController.navigate(menuItem.getItemId(), argumentos, opcionesBuilder.build());

                    ((DrawerLayout) findViewById(R.id.drawer_layout)).closeDrawers();
                }

                return true;
            }
        });
        if(localidad.equals("Santiago de Compostela")){
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_santiago, localidad, localidad);
        }
        else if (localidad.equals("Vigo")) {
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_vigo, localidad, localidad);
        }
        else if (localidad.equals("Lugo")) {
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_lugo, localidad, localidad);
        }
        else if (localidad.equals("Pontevedra")) {
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_pontevedra, localidad, localidad);
        }
        else if (localidad.equals("A Coruña")) {
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_corunha, localidad, localidad);
        }




        // Eventos del menú deslizante
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                // Menú deslizante abierto
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                // Menú deslizante cerrado
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    /*
     private void processFlightOffers(JSONObject response){
        JSONArray flightOffersArray = null;
        try {
            flightOffersArray = response.getJSONArray("data");
            for (int i = 0; i < flightOffersArray.length(); i++) {
                JSONObject flightOfferObject = flightOffersArray.getJSONObject(i);
                // Extraer los valores de los campos necesarios del objeto JSON
                int id = flightOfferObject.getInt("id");
                String origin = flightOfferObject.getString("origin");
                String destination = flightOfferObject.getString("destination");
                String departureDate = flightOfferObject.getString("departureDate");
                double price = flightOfferObject.getDouble("price");

                // Aquí puedes hacer cualquier procesamiento adicional de los datos de vuelo
                // antes de almacenarlos en la base de datos o usarlos en tu aplicación

                // Por ejemplo, puedes imprimir los datos en la consola para verificar que se obtengan correctamente
                Log.d("Flight Data", "Flight ID: " + id);
                Log.d("Flight Data", "Origin: " + origin);
                Log.d("Flight Data", "Destination: " + destination);
                Log.d("Flight Data", "Departure Date: " + departureDate);
                Log.d("Flight Data", "Price: " + price);

                // Aquí puedes insertar los datos en la base de datos o realizar cualquier otra acción necesaria
             }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }-++
     */


    /* Menú principal */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                String apkLink = "https://drive.google.com/file/d/1ex1S0PNlgqqGg1OkjTDyOR5xDz9e3sF8/view?usp=sharing";
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, apkLink);
                startActivity(Intent.createChooser(intent, "Share with"));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {  // Manejo del historial de framentos
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    // Confirmacion de cierre de aplicación
    @Override
    public void onBackPressed() {
        // Fragmentos en la pila de fragmentos
        int count = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment).getChildFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            if (pulsadoDosVecesAtrasParaSalir) { // Ha confirmado con 2 pulsaciones segidas que quiere salir
                super.onBackPressed(); // el codido heredado cerrará la aplicación
                return;
            }

            pulsadoDosVecesAtrasParaSalir = true;
            Toast.makeText(getApplicationContext(), "Por favor, presione ATRAS otra vez para SALIR", Toast.LENGTH_LONG).show();

            new TareaCancelaSalidaApp().execute();

        } else {
            NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
            navController.popBackStack();
        }

    }

    @Override
    public void onLocalidadSeleccionada(String localidadNombre) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        if(localidadNombre.equals("Santiago de Compostela")){
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_santiago, localidadNombre, localidadNombre);
        }
        else if (localidadNombre.equals("Vigo")) {
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_vigo, localidadNombre, localidadNombre);
        }
        else if (localidadNombre.equals("Lugo")) {
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_lugo, localidadNombre, localidadNombre);
        }
        else if (localidadNombre.equals("Pontevedra")) {
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_pontevedra, localidadNombre, localidadNombre);
        }
        else if (localidadNombre.equals("A Coruña")) {
            UtilidadesNavigationDrawer.cambiarCabecera(navigationView, R.drawable.escudo_corunha, localidadNombre, localidadNombre);
        }

    }


    class TareaCancelaSalidaApp extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
                pulsadoDosVecesAtrasParaSalir = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /*
     private void buscarVuelos(String fecha, String origen, String destino, String nave, String hora_salida, String hora_llegada) {
        // Realizar la consulta a la base de datos para buscar los vuelos
        String[] columns = {"_id", "fecha", "origen", "destino", "nave", "hora_salida", "hora_llegada"};
        String selection = "hora_salida = ? AND origen = ? AND hora_llegada = ? AND destino = ?";
        String[] selectionArgs = {hora_salida, origen, hora_llegada, destino};
        Cursor cursor = db.query("vuelos", columns, selection, selectionArgs, null, null, null);

        ArrayList<Vuelo> vuelos = new ArrayList<>();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String fechaVuelo = null;
                int fechaIndex = cursor.getColumnIndex("fecha");
                if (fechaIndex >= 0) {
                    fechaVuelo = cursor.getString(fechaIndex);
                }
                String origenVuelo = null;
                int origenIndex = cursor.getColumnIndex("origen");
                if (origenIndex >= 0) {
                    origenVuelo = cursor.getString(origenIndex);
                }
                String destinoVuelo = null;
                int destinoIndex = cursor.getColumnIndex("destino");
                if (destinoIndex >= 0) {
                    destinoVuelo = cursor.getString(destinoIndex);
                }
                String naveVuelo = null;
                int naveIndex = cursor.getColumnIndex("nave");
                if (naveIndex >= 0) {
                    naveVuelo = cursor.getString(naveIndex);
                }
                String horaSalidaVuelo = null;
                int horaSalidaIndex = cursor.getColumnIndex("hora_salida");
                if (horaSalidaIndex >= 0) {
                    horaSalidaVuelo = cursor.getString(horaSalidaIndex);
                }
                String horaLlegadaVuelo = null;
                int horaLlegadaIndex = cursor.getColumnIndex("hora_llegada");
                if (horaLlegadaIndex >= 0) {
                    horaLlegadaVuelo = cursor.getString(horaLlegadaIndex);
                }
                Vuelo vuelo = new Vuelo(fechaVuelo, origenVuelo, destinoVuelo, naveVuelo, horaSalidaVuelo, horaLlegadaVuelo);
                vuelos.add(vuelo);
                cursor.moveToNext();

            }
        }
          // Crear el intent y pasar los datos de los vuelos a la nueva activity
        Intent intent = new Intent(this, ListaVuelosActivity.class);
        intent.putExtra("vuelos", vuelos);
        startActivity(intent);
     */

}
