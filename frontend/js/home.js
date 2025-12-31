const btnDespesa = document.getElementById('btnDespesa');
const navSub = document.getElementById('navSub');

btnDespesa.addEventListener('click', () => {
    navSub.classList.toggle('ativo');
});