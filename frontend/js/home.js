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

    painelDireito.innerHTML = "";

    const divCabecalho = document.createElement('div');
    const despesaDiv = document.createElement('div');

    divCabecalho.classList.add('cabecalho');
    despesaDiv.classList.add('despesaDiv');

    const divTextos = document.createElement('div');
    divTextos.classList.add('divTextos');
    divTextos.innerHTML = `<h1>Despesas mensais</h1>`
    
    const divBtn = document.createElement('div');

    divBtn.classList.add('divBtnAdd');
    
    divBtn.innerHTML = `<button type="button" class="btn-Opcoes-Des" id="btnFiltroPagas">Pagas</button> | <button type="button" class="btn-Opcoes-Des" id="btnFiltroPendentes">Pendentes</button>
    <button type="button" class="btn-Opcoes-Des" id="btnFiltro">Filtros</button>
    <button type="button" id="btnAdd" class="btn-Opcoes-Des">Adicionar despesa</button>`

    divCabecalho.append(divTextos,divBtn);

    painelDireito.append(divCabecalho,despesaDiv);

    mostrarPendentes(despesaDiv);

    document.getElementById('btnFiltroPagas').addEventListener('click', () => {
        mostrarPagas(despesaDiv);
    });

    document.getElementById('btnFiltroPendentes').addEventListener('click', () => {
        mostrarPendentes(despesaDiv);
    })
});

function mostrarPagas(div){

    div.innerHTML = "";

    listaDespesa.forEach(despesa => {

        const card = document.createElement('div');
        card.classList.add('despesa');

        let sum = 0;

        if(despesa.status === 'Pago'){
            Object.entries(despesa).forEach(([chave, valor]) => {

                
                const campo = document.createElement('div');
                campo.innerHTML = `<p>${titulos[sum]}</p> 
                                    <p>${valor}</p>`

                sum += 1;
                card.appendChild(campo);
            })

            div.appendChild(card);
        }
    });    
}

function mostrarPendentes(div){

    div.innerHTML = "";

    listaDespesa.forEach(despesa => {

        const card = document.createElement('div');
        card.classList.add('despesa');

        let sum = 0;

        if(despesa.status === 'Pendente'){
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
                campo.innerHTML = `<button type="button" class="btn-Opcoes-Des" id="btn${listaOpcoes[i]}">${listaOpcoes[i]}</button>`
                card.appendChild(campo);
            }

            div.appendChild(card);
        }
    });    
}

navSub2.addEventListener('click' ,() => {

    painelDireito.innerHTML = `<div class="cabecalho">
                                <h1>Despesas cotidianas</h1>
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


