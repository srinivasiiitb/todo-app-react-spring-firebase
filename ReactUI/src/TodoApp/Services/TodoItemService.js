import reqHandler from './ReqHandler.js';
import Util from './Util.js'

const collectionName = 'allTodoItems';

const todoItemService = {

    getAllTodoItems() {
        return reqHandler.handleRequest('get', collectionName);
    },

    updateTodoItem(itemId, payload) {
        return reqHandler.handleRequest('patch', Util.getReqURL(collectionName, itemId), payload);
    },

    deleteTodoItem(todoItem) {
        return reqHandler.handleRequest('delete', Util.getReqURL(collectionName, todoItem.id));
    },

    deleteAllTodoItems(reqArray) {
        return reqHandler.processRequestArray(reqArray);
    },

    createNewTodoItem(payload) {
        return reqHandler.handleRequest('post', collectionName, payload);
    },

    getTodoRequestArray(method, todoItems) {
        var reqArray = [];
        todoItems.forEach((todo) => {
            var url = Util.getReqURL(collectionName, todo.id);
            var req = reqHandler.prepareRequest(method, url);
            reqArray.push(req);
        })
        return reqArray;
    }
}
export default todoItemService;
