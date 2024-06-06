const bubbles = document.querySelectorAll('.bubble');

bubbles.forEach(bubble => {
    bubble.addEventListener('click', () => {
        // Eliminar la burbuja
        bubble.style.display = 'none';

        // Crear una nueva imagen
        const image = document.createElement('img');
        image.src = 'imagen.jpg'; // Cambia 'imagen.jpg' por la ruta de tu imagen
        image.style.width = '100%';
        image.style.height = '100%';
        image.style.position = 'absolute';
        image.style.top = '0';
        image.style.left = '0';

        // Agregar la imagen al cuerpo del documento
        document.body.appendChild(image);

        // Hacer que la imagen se mueva aleatoriamente
        const x = Math.random() * window.innerWidth;
        const y = Math.random() * window.innerHeight;
        image.style.transform = `translate(${x}px, ${y}px)`;

        // Eliminar la imagen después de un tiempo
        setTimeout(() => {
            document.body.removeChild(image);
        }, 2000); // Cambia el tiempo según tus preferencias
    });
});