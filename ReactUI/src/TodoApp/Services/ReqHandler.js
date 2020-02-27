import axios from 'axios';

const host = 'http://localhost:8080/';

var headers = {
    'Content-Type': 'application/json'
};

const reqHandler = {

    async handleRequest(method, url, payload) {
        return await axios({
            method: method,
            url: host + url,
            data: payload,
            headers: headers
        });
    },

    prepareRequest(method, url, payload) {
        return axios({
            method: method,
            url: host + url,
            data: payload,
            headers: headers
        });
    },

    processRequestArray(reqArray) {
        return axios.all(reqArray);
    }
}
export default reqHandler;
