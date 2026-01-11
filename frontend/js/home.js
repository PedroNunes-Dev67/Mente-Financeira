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
    diaDePagamento: "28/01",
    titulo:"Faculdade",
    categoria: "Educação",
    valor:504.27,
    status: 'Pendente'
},
{
    diaDePagamento: "30/01",
    titulo:"Cartão",
    categoria: "Despesas Mensais",
    valor:200.50,
    status: 'Pendente'
},
{
    diaDePagamento:"30/01",
    titulo:"Plano da Claro",
    categoria:"Planos/Streaming",
    valor:40.0,
    status:"Pendente"
},
{
    diaDePagamento: "30/01",
    titulo:"Seguro da moto",
    categoria: "Despesas Mensais",
    valor:50.00,
    status: 'Pendente'
},
{
    diaDePagamento:"20/01",
    titulo:"Mercado",
    categoria:"Alimentação",
    valor:600.0,
    status:"Pago"
}
]

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
    divTextos.innerHTML = `<h1>Despesas mensais</h1> <p>Janeiro de 2026</p>`
    
    const divBtn = document.createElement('div');

    divBtn.classList.add('divBtnAdd');
    
    divBtn.innerHTML = `<button type="button" class="btn-Opcoes-Des" id="btnFiltroPagas">Pagas</button> | <button type="button" class="btn-Opcoes-Des" id="btnFiltroPendentes">Pendentes</button>
    <button type="button" class="btn-Opcoes-Des" id="btnFiltro">Filtros</button>
    <button type="button" id="btnAdd" onClick="adicionarDespesa()" class="btn-Opcoes-Des">Adicionar despesa</button>`

    divCabecalho.append(divTextos,divBtn);

    const divNavBar = document.createElement('div');

    const tiposTitulos = ['Data','Titulo','Categoria','Valor']

    
    for (let i = 0; i<4; i++){

        var campoTitulo = document.createElement('div')
        campoTitulo.classList.add('camposValoresDiv')
        campoTitulo.innerHTML += `<h2>${tiposTitulos[i]}</h2>`
        divNavBar.append(campoTitulo)
    }

    divNavBar.innerHTML +=`<div class="camposValoresDiv campoAcoes" id="campoAcoes"><h2>Ações</h2></div>`

    divNavBar.classList.add('divNavBar')

    const divDespesas = document.createElement('div');

    divDespesas.classList.add('style-div-despesas')

    despesaDiv.append(divNavBar, divDespesas)

    painelDireito.append(divCabecalho,despesaDiv);
    
    const btnFiltroPagas = document.getElementById('btnFiltroPagas');
    
    const btnFiltroPendentes = document.getElementById('btnFiltroPendentes');
    
    mostrarPendentes(divDespesas);
    btnFiltroPendentes.classList.add('filtroAtivo')

    btnFiltroPagas.addEventListener('click', () => {
        mostrarPagas(divDespesas);

        btnFiltroPagas.classList.add('filtroAtivo')
        btnFiltroPendentes.classList.remove('filtroAtivo')
    });

    btnFiltroPendentes.addEventListener('click', () => {
        mostrarPendentes(divDespesas);

        btnFiltroPendentes.classList.add('filtroAtivo')
        btnFiltroPagas.classList.remove('filtroAtivo')
    })
});

function mostrarPagas(div){

    div.innerHTML = "";

    listaDespesa.forEach(despesa => {

        const card = document.createElement('div');
        card.classList.add('despesa');

        if(despesa.status === 'Pago'){
            Object.entries(despesa).forEach(([chave, valor]) => {

                if(chave != 'status'){
                    const campo = document.createElement('div');
                    campo.classList.add('camposValoresDiv')
                    campo.innerHTML = `<p>${valor}</p>`

                    card.appendChild(campo);
                }
            })

            const listaOpcoes = ["Pagar", "Excluir", "Atualizar"]

            let campo = document.createElement('div');
            campo.classList.add('camposDeAcoes')

            for(let i =1 ; i<3; i++){
                campo.innerHTML += `<button type="button" class="btn-Opcoes-Des" id="btn${listaOpcoes[i]}">${listaOpcoes[i]}</button>`
            }
            card.appendChild(campo);

            div.appendChild(card);
        }
    });    
}

function mostrarPendentes(div){

    div.innerHTML = "";

    listaDespesa.forEach(despesa => {

        const card = document.createElement('div');
        card.classList.add('despesa');

        if(despesa.status === 'Pendente'){
            Object.entries(despesa).forEach(([chave, valor]) => {

                if (chave != 'status'){
                    const campo = document.createElement('div');
                    campo.classList.add('camposDespesa')
                    campo.classList.add('camposValoresDiv')
                    campo.innerHTML = `<p>${valor}</p>`

                    card.appendChild(campo);
                }
            })

            const listaOpcoes = ["Pagar", "Excluir", "Atualizar"]

            let campo = document.createElement('div');
            campo.classList.add('camposDeAcoes')

            for(let i =0 ; i<3; i++){
                campo.innerHTML += `<button type="button" class="btn-Opcoes-Des" id="btn${listaOpcoes[i]}">${listaOpcoes[i]}</button>`
            }
            card.appendChild(campo);

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

//Faz animação de quando clica em um dos btn da navbar lateral
document.querySelectorAll('.links').forEach((e,i) => {

    e.addEventListener('click', () => {

        document.querySelectorAll('.links').forEach((ele, ind) => {
            ele.classList.remove('btnClicado');
        })

        e.classList.add('btnClicado');
        
    })
})

//Faz animação de quando clica em um dos btn de escohla de despesas da navbar lateral
document.querySelectorAll('.btnEsc').forEach((e,i) => {

    e.addEventListener('click', () => {

        document.querySelectorAll('.btnEsc').forEach((ele, ind) => {
            ele.classList.remove('filtroAtivo');
        })

        e.classList.add('filtroAtivo');
        
    })
})

//Funcao de adicionar uma despesa
function adicionarDespesa(){

    const modal_overlay = document.createElement('div');

    modal_overlay.classList.add('modal_overlay');
    modal_overlay.classList.add('ativoModal')
    modal_overlay.id='modalDespesa';

    const divModal = document.createElement('div');
    divModal.classList.add('modal')

    divModal.innerHTML=" "

    divModal.innerHTML=`
                <h2> ➕ Adicionar despesa</h2>
                <input type="text" placeholder="Titulo">
                <input type="number" placeholder="Dia de pagamento">
                <input type="number" placeholder="Valor">
                <select>
                    <option value="">Categoria</option>
                    <option value="Alimentação">Alimentação</option>
                    <option value="Saúde">Saúde</option>
                    <option value="Despesas mensais">Despesas mensais</option>
                </select>`

    const divAcoes = document.createElement('div');

    divAcoes.classList.add('acoes');

    divAcoes.innerHTML=`
                <button id="btnCancelar" type="button">Cancelar</button>
                <button id="btnSalvar" type="button">Salvar</button>`

    divModal.append(divAcoes);

    modal_overlay.append(divModal);

    painelDireito.append(modal_overlay)


    document.getElementById('btnCancelar').addEventListener('click', () => {

        modal_overlay.classList.remove('ativoModal')

        painelDireito.removeChild(modal_overlay)
    })
}


