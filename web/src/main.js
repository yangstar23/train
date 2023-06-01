import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//注册ant
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';
//引入ant图标
import * as Icons from '@ant-design/icons-vue';

const app = createApp(App);
app.use(Antd).use(store).use(router).mount('#app');


//全局注册ant图标
const icons = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}



