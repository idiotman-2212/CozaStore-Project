$(document).ready(function () {

    $.ajax({

        url: "http://localhost:8080/cart/1",
        method: "get",

    }).done(function (data) {

        console.log("server tra ve ", data);

        const element = document.getElementById("cartTable");

        let htmlAdd = "";

        const lengthData = data.data.length;
        for (let i = 0; i < lengthData; i++) {
            const product = data.data[i];

            htmlAdd += `

            <tr class="table_row">
                <td class="column-1">
                    <div class="how-itemcart1">
                        <img src="../static/images/item-cart-04.jpg" alt="IMG">
                    </div>
                </td>
                <td class="column-2">${product.nameProduct}</td>

                <td class="column-3">$ 36.00</td>

                <td class="column-4">
                    <div class="wrap-num-product flex-w m-l-auto m-sr-0">

                        <div class="btn-num-product-down cl8 hov-btn3 trans-04 flex-c-m">
                            <i class="fs-16 zmdi zmdi-minus"></i>
                        </div>

                        <input class="mtext-104 cl3 txt-center num-product" type="number"
                            name="num-product1" value=${product.quanity}>

                        <div class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m">
                            <i class="fs-16 zmdi zmdi-plus"></i>
                        </div>

                    </div>
                </td>
                <td class="column-5">$ 36.00</td>
            </tr>

            `;

        }

        element.innerHTML = htmlAdd;

    });

});