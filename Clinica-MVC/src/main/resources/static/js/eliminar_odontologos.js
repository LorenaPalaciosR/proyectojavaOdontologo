const tableBody = document.querySelector("#odontologosTable tbody");

function fetchOdontologos() {
  fetch(`/odontologo`)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      tableBody.innerHTML = ""; // Limpiar el contenido actual de la tabla

      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${odontologo.id}</td>
          <td>${odontologo.nombre}</td>
          <td>${odontologo.apellido}</td>
          <td>${odontologo.nroMatricula}</td>
          <td>
          <button class="eliminar-btn" data-id="${odontologo.id}">Eliminar</button>
          </td>
          <td>
            <div id="editForm-${odontologo.id}" style="display: none;">
              <input type="text" id="nombre-${odontologo.id}" value="${odontologo.nombre}" />
              <input type="text" id="apellido-${odontologo.id}" value="${odontologo.apellido}" />
              <input type="text" id="matricula-${odontologo.id}" value="${odontologo.nroMatricula}" />
            </div>
          </td>
        `;
        tableBody.appendChild(row);
      });
    })
    .catch(error => {
      console.error("Error fetching data:", error);
    });
}

document.addEventListener('DOMContentLoaded', function() {
  fetchOdontologos();
  document.addEventListener('click', function(event) {
    if (event.target.classList.contains('eliminar-btn')) {
      const odontologoId = event.target.getAttribute('data-id');
      eliminarOdontologo(odontologoId);
    }
  });
});

function eliminarOdontologo(id) {
  fetch(`/odontologo/${id}`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    }
  })
  .then(response => {
    if (response.ok) {
      console.log('Eliminación exitosa');
      fetchOdontologos(); // Actualizar la lista después de eliminar
      alert('Odontólogo eliminado con éxito!'); // Mostrar alerta de éxito
    } else {
      throw new Error('Error en la eliminación');
    }
  })
  .catch(error => {
    console.error('Error eliminando odontólogo:', error);
  });
}


