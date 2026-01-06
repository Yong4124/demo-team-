document.addEventListener("DOMContentLoaded", function () {
    const lanButtons = document.querySelectorAll(".lan");

    lanButtons.forEach(function (btn) {
        btn.addEventListener("click", function (e) {
            e.preventDefault();
            this.parentElement.classList.toggle("on");
        });
    });

    document.addEventListener("click", function (e) {
        lanButtons.forEach(function (btn) {
            if (e.target !== btn && !btn.contains(e.target)) {
                btn.parentElement.classList.remove("on");
            }
        });
    });
});

document.querySelector(".ac-allmenu").addEventListener("click", function(e) {
    e.preventDefault();
    document.body.classList.toggle("is-nav");
});

