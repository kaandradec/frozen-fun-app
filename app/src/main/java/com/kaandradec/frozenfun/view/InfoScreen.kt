package com.kaandradec.frozenfun.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.kaandradec.frozenfun.R

@Composable
fun InfoScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "Información de la Empresa",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 10.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Nuestra empresa, Frozen Fun, se especializa en la fabricación y venta de helados artesanales de la más alta calidad. Desde nuestra fundación en 2017, nos hemos dedicado a ofrecer una experiencia única a nuestros clientes, con una amplia variedad de sabores y opciones de personalización.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo de la empresa",
                    modifier = Modifier.size(200.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Productos y Servicios",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Ofrecemos una gama diversa de helados, incluyendo opciones de crema, con queso y más!. Además, ofrecemos la posibilidad de personalizar tus helados según tus preferencias.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = R.drawable.helado_nuevos),
                    contentDescription = "Helado Nuevos",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(start = 8.dp) // Agrega un margen a la derecha de la primera imagen
                )
                Image(
                    painter = painterResource(id = R.drawable.pastel_helado_oreo),
                    contentDescription = "Pastel Helado Oreo",
                    modifier = Modifier
                        .size(150.dp)
                )
            }
        }

        item {
            Text(
                text = "¿Quiénes Somos?",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "En Frozen Fun, somos un equipo apasionado por los helados, comprometidos con la innovación y la satisfacción del cliente. Nuestro equipo directivo cuenta con años de experiencia en la industria gastronómica, asegurando que cada helado que hacemos cumpla con los más altos estándares de calidad.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        item {
            Text(
                text = "Compromiso Social y Ambiental",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "En Frozen Fun, nos preocupamos por el medio ambiente. Utilizamos envases biodegradables y apoyamos a proveedores locales de ingredientes frescos y sostenibles. Además, estamos comprometidos con prácticas de comercio justo en todas nuestras operaciones.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.envase),
                    contentDescription = "Envase biodegradable",
                    modifier = Modifier.size(200.dp)
                )
            }
        }

        item {
            Text(
                text = "Premios y Reconocimientos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Nos enorgullece haber recibido varios premios por la calidad de nuestros helados y nuestro compromiso con la comunidad. Estos reconocimientos reflejan nuestro compromiso constante con la excelencia y la satisfacción del cliente.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.premio),
                    contentDescription = "Premio",
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}
