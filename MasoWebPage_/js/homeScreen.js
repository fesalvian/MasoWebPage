
document.addEventListener('DOMContentLoaded', async () => {

    const { default: filterAndSearch } = await import('./modulo.js');
    const cards = document.querySelectorAll('.card');

        //funcao de redirecionamento dos cards da home page
        cards.forEach(card => {
            card.addEventListener('click', () => {
                //console.log("card de filtro clicado");
                const filter = card.getAttribute('data-filter');
                //console.log(`Redirecting to catalogo.html with filter: ${filter}`);
                window.location.href = `pages/catalogo.html?filter=${filter}`;
            });
        });

        // Verifica se há um parâmetro de filtro na URL e aplica o filtro correspondente

const urlParams = new URLSearchParams(window.location.search);
const filter = urlParams.get('filter');
if (filter) {
    filterSelect.value = filter;
    filterAndSearch(filter, searchInput.value.toLowerCase());
};

console.log("arquivo homescreen carregado com sucesso");

});