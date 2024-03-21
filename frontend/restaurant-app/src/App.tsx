import {Layout, Result, Spin} from "antd";
import Navbar from "./layout/navbar/Navigation.tsx";
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import {lazy, Suspense} from "react";
import AppFooter from "./layout/footer/AppFooter.tsx";
import ModifyMenuPage from "./pages/ModifyMenuPage.tsx";
import CreateMenuPage from "./pages/CreateMenuPage.tsx";
import CreateRestaurantPage from "./pages/CreateRestaurantPage.tsx";

const HomePage = lazy(() => import("./pages/HomePage.tsx"));
const RestaurantsListPage = lazy(() => import("./pages/RestaurantsListPage.tsx"));
const RestaurantPage = lazy(() => import("./pages/RestaurantPage.tsx"));
const OrderTicketsPage = lazy(() => import("./pages/OrderTicketsPage.tsx"));
const MenusPage = lazy(() => import("./pages/MenusPage.tsx"));
const ProfilePage = lazy(() => import("./pages/ProfilePage.tsx"));


function App() {

  return (
      <BrowserRouter>
        <Layout style={{
          overflow: 'auto',
          minHeight: '100vh',
          width: '100%',
          left: 0,
        }}>
          <Navbar/>
          <Layout>
            <Suspense fallback={<Spin fullscreen spinning={true}/>}>
              <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/restaurants" element={<RestaurantsListPage/>}>
                  <Route path="add" element={<CreateRestaurantPage/>}/>
                </Route>
                <Route path="/restaurants/:id" element={<RestaurantPage/>}>
                  <Route path="orders" element={<OrderTicketsPage/>}/>
                  <Route path="menu" element={<MenusPage/>}>
                    <Route path="add" element={<CreateMenuPage/>}/>
                    <Route path=":menuId/edit" element={<ModifyMenuPage/>}/>
                  </Route>
                </Route>
                <Route path="/profile" element={<ProfilePage/>}/>
                <Route path="*" element={<Result
                    status="404"
                    title="404"
                    subTitle="Sorry, the page you visited does not exist."
                    extra={<Link to={"/"}>Back Home</Link>}
                />}/>
              </Routes>
            </Suspense>
          </Layout>
          <AppFooter/>
        </Layout>
      </BrowserRouter>

  )
}

export default App
