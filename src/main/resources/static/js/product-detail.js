$(document).ready(function () {
    // Lấy productId từ URL
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get("productId");


    $.ajax({
        url: `http://localhost:8080/product/${productId}`, // Truy vấn sản phẩm chi tiết dựa trên productId
        method: "get",
    }).done(function (data) {
        console.log("server tra ve ", data);

        const element = document.getElementById("productDetail");
        let htmlAdd = "";

        const product = data.data[0]; // Lấy sản phẩm đầu tiên từ kết quả truy vấn
        if (product) {

            // Hiển thị thông tin sản phẩm chi tiết
            htmlAdd = `

                <div class="row" >
                            <div class="col-md-6 col-lg-7 p-b-30">
                                <div class="p-l-25 p-r-30 p-lr-0-lg">
                                    <div class="wrap-slick3 flex-sb flex-w">
                                        <div class="wrap-slick3-dots"></div>
                                        <div class="wrap-slick3-arrows flex-sb-m flex-w"></div>
                                        <div class="slick3 gallery-lb">

                                            <div class="item-slick3" data-thumb="images/${product.image}">
                                                <div class="wrap-pic-w pos-relative">
                                                    <img alt="IMG-PRODUCT" src="images/${product.image}">
                                                    <a class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04"
                                                       href="images/${product.image}">
                                                        <i class="fa fa-expand"></i>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="item-slick3" data-thumb="images/${product.image}">
                                                <div class="wrap-pic-w pos-relative">
                                                    <img alt="IMG-PRODUCT" src="images/${product.image}">
                                                    <a class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04"
                                                       href="images/${product.image}">
                                                        <i class="fa fa-expand"></i>
                                                    </a>
                                                </div>
                                            </div>

                                            <div class="item-slick3" data-thumb="images/${product.image}">
                                                <div class="wrap-pic-w pos-relative">
                                                    <img alt="IMG-PRODUCT" src="images/${product.image}">
                                                    <a class="flex-c-m size-108 how-pos1 bor0 fs-16 cl10 bg0 hov-btn3 trans-04"
                                                       href="images/${product.image}">
                                                        <i class="fa fa-expand"></i>
                                                    </a>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6 col-lg-5 p-b-30">
                                <div class="p-r-50 p-t-5 p-lr-0-lg">
                                    <h4 class="mtext-105 cl2 js-name-detail p-b-14">
                                        ${product.nameProduct}
                                    </h4>
                                    <span class="mtext-106 cl2">
                							$${product.price}
                						</span>
                                    <p class="stext-102 cl3 p-t-23">
                                        ${product.description}
                                    </p>

                                    <div class="p-t-33">
                                        <div class="flex-w flex-r-m p-b-10">
                                                        <div class="size-203 flex-c-m respon6">
                                                            Size
                                                        </div>
                                                        <div class="size-204 respon6-next">
                                                            <div class="rs1-select2 bor8 bg0">
                                                                <select class="js-select2" name="time" id="sizeSelect">
                                                                    <option>${product.nameSize}</option>
                                                                </select>
                                                                <div class="dropDownSelect2"></div>
                                                            </div>
                                                        </div>
                                                    </div>

                                        <div class="flex-w flex-r-m p-b-10">
                                            <div class="size-203 flex-c-m respon6">
                                                Color
                                            </div>
                                            <div class="size-204 respon6-next">
                                                <div class="rs1-select2 bor8 bg0">
                                                    <select class="js-select2" name="time" id="colorSelect">
                                                        <option>${product.nameColor}</option>
                                                    </select>
                                                    <div class="dropDownSelect2"></div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="flex-w flex-r-m p-b-10">
                                            <div class="size-204 flex-w flex-m respon6-next">
                                                <div class="wrap-num-product flex-w m-r-20 m-tb-10">
                                                    <div class="btn-num-product-down cl8 hov-btn3 trans-04 flex-c-m">
                                                        <i class="fs-16 zmdi zmdi-minus"></i>
                                                    </div>

                                                    <input class="mtext-104 cl3 txt-center num-product" name="num-product"
                                                           type="number" value="1">

                                                    <div class="btn-num-product-up cl8 hov-btn3 trans-04 flex-c-m">
                                                        <i class="fs-16 zmdi zmdi-plus"></i>
                                                    </div>

                                                </div>
                                                <button class="flex-c-m stext-101 cl0 size-101 bg1 bor1 hov-btn1 p-lr-15 trans-04 js-addcart-detail">
                                                    Add to cart
                                                </button>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="flex-w flex-m p-l-100 p-t-40 respon7">
                                        <div class="flex-m bor9 p-r-10 m-r-11">
                                            <a class="fs-14 cl3 hov-cl1 trans-04 lh-10 p-lr-5 p-tb-2 js-addwish-detail tooltip100"
                                               data-tooltip="Add to Wishlist"
                                               href="#">
                                                <i class="zmdi zmdi-favorite"></i>
                                            </a>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="bor10 m-t-50 p-t-43 p-b-40">

                            <div class="tab01">

                                <ul class="nav nav-tabs" role="tablist">
                                    <li class="nav-item p-b-10">
                                        <a class="nav-link active" data-toggle="tab" href="#description" role="tab">Description</a>
                                    </li>
                                    <li class="nav-item p-b-10">
                                        <a class="nav-link" data-toggle="tab" href="#information" role="tab">Additional
                                            information</a>
                                    </li>
                                    <li class="nav-item p-b-10">
                                        <a class="nav-link" data-toggle="tab" href="#reviews" role="tab">Reviews (1)</a>
                                    </li>
                                </ul>

                                <div class="tab-content p-t-43">

                                    <div class="tab-pane fade show active" id="description" role="tabpanel">
                                        <div class="how-pos2 p-lr-15-md">
                                            <p class="stext-102 cl6">${product.description}
                                            </p>
                                        </div>
                                    </div>

                                    <div class="tab-pane fade" id="information" role="tabpanel">
                                        <div class="row">
                                            <div class="col-sm-10 col-md-8 col-lg-6 m-lr-auto">

                                            </div>
                                        </div>
                                    </div>

                                    <div class="tab-pane fade" id="reviews" role="tabpanel">
                                        <div class="row">
                                            <div class="col-sm-10 col-md-8 col-lg-6 m-lr-auto">
                                                <div class="p-b-30 m-lr-15-sm">

                                                    <div class="flex-w flex-t p-b-68">
                                                        <div class="wrap-pic-s size-109 bor0 of-hidden m-r-18 m-t-6">
                                                            <img alt="AVATAR" src="images/avatar-01.jpg">
                                                        </div>
                                                        <div class="size-207">
                                                            <div class="flex-w flex-sb-m p-b-17">
                													<span class="mtext-107 cl2 p-r-20">
                														Ariana Grande
                													</span>
                                                                <span class="fs-18 cl11">
                														<i class="zmdi zmdi-star"></i>
                														<i class="zmdi zmdi-star"></i>
                														<i class="zmdi zmdi-star"></i>
                														<i class="zmdi zmdi-star"></i>
                														<i class="zmdi zmdi-star-half"></i>
                													</span>
                                                            </div>
                                                            <p class="stext-102 cl6">
                                                                Quod autem in homine praestantissimum atque optimum est, id
                                                                deseruit. Apud ceteros autem philosophos
                                                            </p>
                                                        </div>
                                                    </div>

                                                    <form class="w-full">
                                                        <h5 class="mtext-108 cl2 p-b-7">
                                                            Add a review
                                                        </h5>
                                                        <p class="stext-102 cl6">
                                                            Your email address will not be published. Required fields are marked *
                                                        </p>
                                                        <div class="flex-w flex-m p-t-50 p-b-23">
                												<span class="stext-102 cl3 m-r-16">
                													Your Rating
                												</span>
                                                            <span class="wrap-rating fs-18 cl11 pointer">
                													<i class="item-rating pointer zmdi zmdi-star-outline"></i>
                													<i class="item-rating pointer zmdi zmdi-star-outline"></i>
                													<i class="item-rating pointer zmdi zmdi-star-outline"></i>
                													<i class="item-rating pointer zmdi zmdi-star-outline"></i>
                													<i class="item-rating pointer zmdi zmdi-star-outline"></i>
                													<input class="dis-none" name="rating" type="number">
                												</span>
                                                        </div>
                                                        <div class="row p-b-25">
                                                            <div class="col-12 p-b-5">
                                                                <label class="stext-102 cl3" for="review">Your review</label>
                                                                <textarea class="size-110 bor8 stext-102 cl2 p-lr-20 p-tb-10"
                                                                          id="review" name="review"></textarea>
                                                            </div>
                                                            <div class="col-sm-6 p-b-5">
                                                                <label class="stext-102 cl3" for="name">Name</label>
                                                                <input class="size-111 bor8 stext-102 cl2 p-lr-20" id="name"
                                                                       name="name" type="text">
                                                            </div>
                                                            <div class="col-sm-6 p-b-5">
                                                                <label class="stext-102 cl3" for="email">Email</label>
                                                                <input class="size-111 bor8 stext-102 cl2 p-lr-20" id="email"
                                                                       name="email" type="text">
                                                            </div>
                                                        </div>
                                                        <button
                                                                class="flex-c-m stext-101 cl0 size-112 bg7 bor11 hov-btn3 p-lr-15 trans-04 m-b-10">
                                                            Submit
                                                        </button>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

            `;

        } else {
            htmlAdd = `<p>Product not found</p>`;
        }

        element.innerHTML = htmlAdd;
    });
});

