// utils.js

// Helper: Get JWT token from storage
function getToken() {
    return localStorage.getItem("token");
}

// Helper: Add auth header
function authHeaders() {
    return {
        "Authorization": "Bearer " + getToken(),
        "Content-Type": "application/json"
    };
}

// Optional: Decode token (basic payload)
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
