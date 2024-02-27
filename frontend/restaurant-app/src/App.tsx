import {Layout} from "antd";
import Navbar from "./layout/navbar/Navigation.tsx";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import {lazy, Suspense} from "react";
import CreateMenuPage from "./pages/CreateMenuPage.tsx";

const HomePage = lazy(() => import("./pages/HomePage.tsx"))
const RestaurantsListPage = lazy(() => import("./pages/RestaurantsListPage.tsx"))
const RestaurantPage = lazy(() => import("./pages/RestaurantPage.tsx"))
const OrdersPage = lazy(() => import("./pages/OrdersPage.tsx"))
const MenusPage = lazy(() => import("./pages/MenusPage.tsx"))


function App() {

  return (
      <BrowserRouter>
        <Layout>
          <Navbar/>
          <Suspense fallback={<div>Loading...</div>}>
            <Routes>
              <Route path="/" element={<HomePage/>}/>
              <Route path="/restaurants" element={<RestaurantsListPage/>}/>
              <Route path="/restaurants/:id" element={<RestaurantPage/>}>
                <Route path="orders" element={<OrdersPage/>}/>
                <Route path="menu" element={<MenusPage/>}>
                  <Route path="add" element={<CreateMenuPage />}/>
                </Route>
              </Route>
              <Route path="*" element={<div>404</div>}/>
            </Routes>
          </Suspense>
        </Layout>
      </BrowserRouter>

  )
}

export default App
