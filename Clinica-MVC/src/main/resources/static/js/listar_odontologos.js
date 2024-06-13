const tableBody = document.querySelector("#odontologosTable tbody");
function fetchOdontologos() {
  // listando los odontologos

  fetch(`/odontologo`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
                <td>${odontologo.id}</td>
                <td>${odontologo.nombre}</td>
                <td>${odontologo.apellido}</td>
                <td>${odontologo.nroMatricula}</td>
                `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });

  // modificar un odontologo

  // eliminar un odontologo
}

fetchOdontologos();
