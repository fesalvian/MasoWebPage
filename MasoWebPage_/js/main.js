
document.addEventListener('DOMContentLoaded', () => {

    const catalogItems = document.querySelectorAll('.catalog-item');
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

    //logo redireciona pra homeScreen
    document.querySelector('.logoMaso').addEventListener('click', function() {
        const currentPath = window.location.pathname;
    
        if (!currentPath.includes('/home.html')) {
            window.location.href = '../home.html';
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

    console.log("arquivo main carregado com sucesso");
});