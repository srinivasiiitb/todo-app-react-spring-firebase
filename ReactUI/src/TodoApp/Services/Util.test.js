import Util from "./Util.js";

it("Return correct value", () => {
 const url = Util.getReqURL("Appname","somepath");
 expect(url).toEqual("Appname/somepath");
});