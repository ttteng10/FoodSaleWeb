function count(type){
    const result = document.getElementById('resultnum');
    const quantity = document.getElementById('quantity');
    const price = document.getElementById('price_sale').innerHTML;
    const changeprice = document.getElementById('price_change');

    let num = result.innerText;
    let now = price.toString().replace(/,/g, "");
    let money=0;
    if(type==='plus'){
        num = parseInt(num)+1;
        money = parseInt(num)*parseInt(now);
    }else if(type === 'minus'){
        if(num==='1'){
            num=1;
            money = parseInt(num)*parseInt(now);
        }else{
            num = parseInt(num)-1;
            money = parseInt(num)*parseInt(now);
        }
    }
    result.innerText = num;
    quantity.value = parseInt(num);
    changeprice.innerText=money.toLocaleString('ko-KR');;
}

