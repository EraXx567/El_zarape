document.addEventListener('DOMContentLoaded', function () {
    const cantidadValoresInput = document.getElementById('cantidadValores');
    const valoresContainer = document.getElementById('valoresContainer');
    const resultadoDiv = document.getElementById('resultado');
    const mediaResultado = document.getElementById('mediaResultado');

    cantidadValoresInput.addEventListener('change', function () {
        // Obtener la cantidad de valores que el usuario quiere ingresar
        const n = parseInt(cantidadValoresInput.value);

        // Limpiar el contenedor de valores
        valoresContainer.innerHTML = '';

        // Generar los campos de entrada de valores si la cantidad es válida
        if (n >= 5 && n <= 15) {
            for (let i = 0; i < n; i++) {
                const inputGroup = document.createElement('div');
                inputGroup.className = 'mb-2';

                const label = document.createElement('label');
                label.className = 'form-label';
                label.textContent = `Número ${i + 1} (10-100):`;

                const input = document.createElement('input');
                input.type = 'number';
                input.className = 'form-control';
                input.min = 10;
                input.max = 100;
                input.required = true;

                inputGroup.appendChild(label);
                inputGroup.appendChild(input);
                valoresContainer.appendChild(inputGroup);
            }
        }
    });

    // Cambia el evento al 'submit' del formulario
    document.getElementById('mediaForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Prevenir el envío del formulario

        // Obtener todos los valores ingresados por el usuario
        const inputs = valoresContainer.getElementsByTagName('input');
        const valores = [];
        
        for (let input of inputs) {
            const valor = parseFloat(input.value);
            if (valor < 10 || valor > 100) {
                alert("Todos los números deben estar entre 10 y 100.");
                return;
            }
            valores.push(valor);
        }

        // Calcular la media
        const suma = valores.reduce((acc, curr) => acc + curr, 0);
        const media = suma / valores.length;

        // Mostrar el resultado
        mediaResultado.textContent = media.toFixed(2);
        resultadoDiv.style.display = 'block';
    });
});
