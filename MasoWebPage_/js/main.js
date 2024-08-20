document.addEventListener('DOMContentLoaded', () => {

    const favorites = JSON.parse(localStorage.getItem('favorites')) || [];

    //resposta visual dos botoes de navegação (ficam em branco na pagina que vc esta)
    const navButtons = document.querySelectorAll('.navButtons .btn');
    const currentPage = window.location.pathname.split('/').pop();
    navButtons.forEach(button => {
        const buttonPage = button.closest('a').getAttribute('href'); // Obtém o href do link pai
        if (buttonPage === currentPage) {
        button.classList.add('active');
        }
    });



// Carregar favoritos do localStorage
    function loadFavorites() {
        catalogItems.forEach(item => {
            const itemId = item.dataset.id;
            if (favorites.includes(itemId)) {
                item.classList.add('favorite');
                const checkbox = item.querySelector('.favorite-checkbox');
                if (checkbox) {
                    checkbox.checked = true;
                }
            }
        });
    }

    loadFavorites();

    // Seta o valor inicial da checkbox baseado no localstorage
    const checkboxes = document.querySelectorAll('.favorite-checkbox');
    checkboxes.forEach(checkbox => {
        const catalogItem = checkbox.closest('.catalog-item');
        const itemId = catalogItem.getAttribute('data-id');
        if (favorites.includes(itemId)) {
            checkbox.checked = true;
        }

        checkbox.addEventListener('change', function() {
            if (this.checked) {
                addToFavorites(itemId);
            } else {
                removeFromFavorites(itemId);
            }
        });
    });
});