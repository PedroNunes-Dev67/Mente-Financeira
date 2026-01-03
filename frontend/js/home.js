const btnDespesa = document.getElementById('btnDespesa');
const navSub = document.getElementById('navSub');
const painelDireito = document.getElementById('painelDireito');

const btnDashboard = document.getElementById('btnDashboard');
const btnHistorico = document.getElementById('btnHistorico');
const btnSuporte = document.getElementById('btnSuporte');
const btnConfig = document.getElementById('btnConfig');
const navSub1 = document.getElementById('navSub1');
const navSub2 = document.getElementById('navSub2');

const listaDespesa = [
    {
    diaDePagamento: "20/01",
    titulo:"Academia",
    categoria: "Saúde",
    valor:75.0,
    status: 'Pendente'
},
{
    diaDePagamento:"20/01",
    titulo:"Mercado",
    categoria:"Alimentação",
    valor:600.0,
    status:"Pago"
},
{
    diaDePagamento:"20/01",
    titulo:"Mercado",
    categoria:"Alimentação",
    valor:600.0,
    status:"Pago"
},
{
    diaDePagamento:"20/01",
    titulo:"Mercado",
    categoria:"Alimentação",
    valor:600.0,
    status:"Pago"
},
{
    diaDePagamento:"20/01",
    titulo:"Mercado",
    categoria:"Alimentação",
    valor:600.0,
    status:"Pago"
},
{
    diaDePagamento:"20/01",
    titulo:"Mercado",
    categoria:"Alimentação",
    valor:600.0,
    status:"Pago"
},
{
    diaDePagamento:"20/01",
    titulo:"Mercado",
    categoria:"Alimentação",
    valor:600.0,
    status:"Pago"
}
]

const titulos = ["Data de pagamneto", "Titulo", "Categoria","Valor","Status"]

btnDespesa.addEventListener('click', () => {

    navSub.classList.toggle('ativo');

    const imgArrow = document.getElementById('imgArrow');

     imgArrow.src = navSub.classList.contains('ativo')
        ? '../assets/images/arrow-down-sign-to-navigate2.png'
        : '../assets/images/arrow-down-sign-to-navigate.png';

});

btnDashboard.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div class="cabecalho">
                                <h1>Dashboard</h1>
                              </div>
                              <div class="dashPrincipal">
                                <div class="dashDivEsq">
                                    <div class="divEsqSon"></div>
                                    <div class="divEsqSon"></div>
                                </div>
                                <div class="dashDivDir">
                                    <img src="../assets/images/grafico_pizza.jpg" alt="">
                                </div>
                            </div>`

});

btnHistorico.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div class="cabecalho">
                                <h1>Histórico</h1>
                               </div>`

});

btnConfig.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div class="cabecalho">
                                <h1>Configurações</h1>
                               </div>`
;

});

btnSuporte.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div class="cabecalho">
                                <h1>Suporte</h1>
                               </div>`
;

});

navSub1.addEventListener('click' ,() => {

    const divCabecalho = document.createElement('div');
    const despesaDiv = document.createElement('div');

    divCabecalho.classList.add('cabecalho');
    despesaDiv.classList.add('despesaDiv');
    
    divCabecalho.innerHTML = `<h1>Despesas mensais</h1> 
                                <p>Categoria | Status | Data</p>`

    painelDireito.innerHTML = "";

    painelDireito.append(divCabecalho,despesaDiv);

    listaDespesa.forEach(despesa => {

        const card = document.createElement('div');
        card.classList.add('despesa');

        let sum = 0;
        Object.entries(despesa).forEach(([chave, valor]) => {

            
            const campo = document.createElement('div');
            campo.innerHTML = `<p>${titulos[sum]}</p> 
                                <p>${valor}</p>`

            sum += 1;
            card.appendChild(campo);
        })


        const listaOpcoes = ["Pagar", "Excluir", "Atualizar"]

        for(let i =0 ; i<3; i++){
            const campo = document.createElement('div');
            campo.innerHTML = `${listaOpcoes[i]}`
            card.appendChild(campo);
        }

        despesaDiv.appendChild(card);
    });
});

navSub2.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div class="cabecalho">
                                <h1>Despesas cotidianas</h1>
                                <p>Categoria | Status | Data</p>
                               </div>` 
;

});

navSub3.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div class="cabecalho">
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


