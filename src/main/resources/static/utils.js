// utils.js


function getToken() {
    return localStorage.getItem("token");
}


function authHeaders() {
    return {
        "Authorization": "Bearer " + getToken(),
        "Content-Type": "application/json"
    };
}


function getDecodedToken() {
    try {
        const token = getToken();
        if (!token) return null;
        const payload = token.split('.')[1];
        return JSON.parse(atob(payload));
    } catch (err) {
        return null;
    }
}
