function pageRedirect(page) {
    document.write("You will be redirected to new page in few seconds");
    setTimeout(window.location.href = page,3000);
}
