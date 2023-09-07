function count(type){
    const result = document.getElementById('resultnum');
    const price = document.getElementById('price_hidden').innerHTML;
    const changeprice = document.getElementById('price_change');
    const totalprice = document.getElementById("shopbasket_total_price");
    const deliveryprice = document.getElementById('delivery_price').innerHTML;
    const finalprice = document.getElementById('final_price');

    let num = result.innerText;
    let now = price.toString().replace(/,/g, "");
    let delivery = deliveryprice.toString().replace(/,/g, "");
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
    let final = parseInt(delivery)+parseInt(money);
    result.innerText = num;
    changeprice.innerText=money.toLocaleString('ko-KR');
    totalprice.innerText=money.toLocaleString('ko-KR');
    finalprice.innerText=final.toLocaleString('ko-KR');
}

window.onload = function(){
    const price = document.getElementById('price_hidden').innerHTML;
    const totalprice = document.getElementById("shopbasket_total_price");
    const deliveryprice = document.getElementById('delivery_price').innerHTML;
    const finalprice = document.getElementById('final_price');
//
//    const result = document.getElementById('resultnum');
//    const changeprice = document.getElementById('price_change');
//    let changeMoney = changeprice.toString().replace(/,/g, "");
//    let resultMoney = parseInt(changeMoney) * parseInt(result);
//    changeprice.innerText = changeMoney.toLocaleString('ko-KR');
    
    let delivery = deliveryprice.toString().replace(/,/g, "");
    let money = parseInt(totalprice)+parseInt(delivery);
    if(isNaN(totalprice)){
        money=0;
    }
//    totalprice.innerText=price.toLocaleString('ko-KR');
//    finalprice.innerText=money.toLocaleString('ko-KR');
};
