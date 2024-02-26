import HomePage from "./pages/HomePage.tsx";
import {Layout} from "antd";
import Navbar from "./layout/navbar/Navigation.tsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import RestaurantsListPage from "./pages/RestaurantsListPage.tsx";
import RestaurantPage from "./pages/RestaurantPage.tsx";
import OrdersPage from "./pages/OrdersPage.tsx";
import MenusPage from "./pages/MenusPage.tsx";


function App() {

  return (
      <BrowserRouter>
        <Layout>
          <Navbar/>
          <Routes>
            <Route path="/" element={<HomePage/>}/>
            <Route path="/restaurants" element={<RestaurantsListPage/>}/>
            <Route path="/restaurants/:id" element={<RestaurantPage/>}>
              <Route path="orders" element={<OrdersPage/>}/>
              <Route path="menu" element={<MenusPage/>}/>
            </Route>
            <Route path="*" element={<div>404</div>}/>
          </Routes>
        </Layout>
      </BrowserRouter>
  )
}

export default App
