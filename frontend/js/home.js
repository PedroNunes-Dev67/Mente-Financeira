const btnDespesa = document.getElementById('btnDespesa');
const navSub = document.getElementById('navSub');
const painelDireito = document.getElementById('painelDireito');

const btnDashboard = document.getElementById('btnDashboard');
const btnHistorico = document.getElementById('btnHistorico');
const btnSuporte = document.getElementById('btnSuporte');
const btnConfig = document.getElementById('btnConfig');
const navSub1 = document.getElementById('navSub1');
const navSub2 = document.getElementById('navSub2');

const despesa = {
    titulo:"Academia",
    diaDePagamento: "20/01",
    categoria: "Saúde",
    valor:75.0,
    status: 'Pendente'
}

btnDespesa.addEventListener('click', () => {

    navSub.classList.toggle('ativo');

    const imgArrow = document.getElementById('imgArrow');

     imgArrow.src = navSub.classList.contains('ativo')
        ? '../assets/images/arrow-down-sign-to-navigate2.png'
        : '../assets/images/arrow-down-sign-to-navigate.png';

});

btnDashboard.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Dashboard</h1>
                              </div>
                              <div class="dashPrincipal">
                                <div class="dashDivEsq">
                                    <div class="divEsqSon"><img src="../assets/images/grafico_horizontal1.png" alt=""></div>
                                    <div class="divEsqSon"><img src="../assets/images/grafico_horizontal2.png" alt=""></div>
                                </div>
                                <div class="dashDivDir">
                                    <img src="../assets/images/grafico_pizza.jpg" alt="">
                                </div>
                            </div>`

});

btnHistorico.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Histórico</h1>
                               </div>`

});

btnConfig.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Configurações</h1>
                               </div>`
;

});

btnSuporte.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Suporte</h1>
                               </div>`
;

});

navSub1.addEventListener('click' ,() => {

    painelDireito.innerHTML = ` <div id="cabecalho">
                                    <h1>Despesas mensais</h1>
                                    <p>Categoria | Status | Data</p>
                                </div>
                                <div class="despesaDiv">
                                    <div class="despesa">
                                        <div>
                                            <p>Data de pagamento</p><hr>
                                            <p>${despesa.diaDePagamento}</p>
                                        </div>
                                        <div>
                                            <p>Titulo</p><hr>
                                            <p>${despesa.titulo}</p>
                                        </div>
                                        <div>
                                            <p>Categoria</p><hr>
                                            <p>${despesa.categoria}</p>
                                        </div>
                                        <div>
                                            <p>Valor</p><hr>
                                            <p>R$${despesa.valor}</p>
                                        </div>
                                        <div>
                                            <p>Status</p><hr>
                                            <p>${despesa.status}</p>
                                        </div>
                                        <div>
                                            <p>Pagar</p>
                                        </div>
                                        <div>
                                            <p>Excluir</p>
                                        </div>
                                        <div>
                                            <p>Atualizar</p>
                                        </div>
                                    </div>
                                </div>`;

});

navSub2.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Despesas cotidianas</h1>
                                <p>Categoria | Status | Data</p>
                               </div>` 
;

});

navSub3.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div id="cabecalho">
                                <h1>Total de gastos mensal</h1>
                               </div>`
;

});

document.querySelectorAll('.links').forEach((e,i) => {

    e.addEventListener('click', () => {

        document.querySelectorAll('.links').forEach((ele, ind) => {
            ele.classList.remove('btnClicado');
        })

        e.classList.add('btnClicado');
        
    })
})


