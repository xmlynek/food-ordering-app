import {Layout, Result, Spin} from "antd";
import Navbar from "./layout/navbar/Navigation.tsx";
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import {lazy, Suspense} from "react";

const HomePage = lazy(() => import("./pages/HomePage.tsx"))
const RestaurantsListPage = lazy(() => import("./pages/RestaurantsListPage.tsx"))
const RestaurantPage = lazy(() => import("./pages/RestaurantPage.tsx"))
const OrdersPage = lazy(() => import("./pages/OrdersPage.tsx"))
const MenusPage = lazy(() => import("./pages/MenusPage.tsx"))
const CreateRestaurantPage = lazy(() => import("./pages/CreateRestaurantPage.tsx"))
const CreateMenuPage = lazy(() => import("./pages/CreateMenuPage.tsx"))


function App() {

  return (
      <BrowserRouter>
        <Layout>
          <Navbar/>
          <Suspense fallback={<Spin fullscreen spinning={true}/>}>
            <Routes>
              <Route path="/" element={<HomePage/>}/>
              <Route path="/restaurants" element={<RestaurantsListPage/>}>
                <Route path="add" element={<CreateRestaurantPage/>}/>
              </Route>
              <Route path="/restaurants/:id" element={<RestaurantPage/>}>
                <Route path="orders" element={<OrdersPage/>}/>
                <Route path="menu" element={<MenusPage/>}>
                  <Route path="add" element={<CreateMenuPage/>}/>
                </Route>
              </Route>
              <Route path="*" element={<Result
                  status="404"
                  title="404"
                  subTitle="Sorry, the page you visited does not exist."
                  extra={<Link to={"/"}>Back Home</Link>}
              />}/>
            </Routes>
          </Suspense>
        </Layout>
      </BrowserRouter>

  )
}

export default App
