let products = JSON.parse(localStorage.getItem("products")) || [];

const dashboard = document.getElementById("dashboardCards");
const alertSections = document.getElementById("alertSections");
const alertBtn = document.getElementById("alertCountBtn");
const formContainer = document.getElementById("productFormContainer");
const form = document.getElementById("productForm");
const addBtn = document.getElementById("addProductBtn");

// Toggle Add Product Form
addBtn.onclick = () => {
  formContainer.classList.toggle("hidden");
};

// Handle Form Submit
form.addEventListener("submit", (e) => {
  e.preventDefault();
  const name = document.getElementById("productName").value;
  const expiry = document.getElementById("expiryDate").value;
  const quantity = parseInt(document.getElementById("quantity").value);

  const product = { name, expiry, quantity };
  products.push(product);
  localStorage.setItem("products", JSON.stringify(products));
  form.reset();
  formContainer.classList.add("hidden");
  renderUI();
});

// Get Stats
function getStats() {
  const today = new Date().toISOString().split("T")[0];
  const total = products.length;
  const expired = products.filter(p => p.expiry < today).length;
  const fresh = products.filter(p => p.expiry >= today).length;
  const lowStock = products.filter(p => p.quantity <= 5).length;
  return { total, expired, fresh, lowStock };
}

// Render Dashboard Cards
function renderDashboard() {
  const stats = getStats();
  dashboard.innerHTML = `
    <div class="bg-white p-4 rounded-xl shadow text-center hover:scale-105 transition-all">
      <div class="text-sm">🧺 Total</div>
      <div class="text-xl font-bold text-blue-600">${stats.total}</div>
    </div>
    <div class="bg-white p-4 rounded-xl shadow text-center hover:scale-105 transition-all">
      <div class="text-sm">🌿 Fresh</div>
      <div class="text-xl font-bold text-green-600">${stats.fresh}</div>
    </div>
    <div class="bg-white p-4 rounded-xl shadow text-center hover:scale-105 transition-all">
      <div class="text-sm">⛔ Expired</div>
      <div class="text-xl font-bold text-red-500">${stats.expired}</div>
    </div>
    <div class="bg-white p-4 rounded-xl shadow text-center hover:scale-105 transition-all">
      <div class="text-sm">📉 Low Stock</div>
      <div class="text-xl font-bold text-orange-500">${stats.lowStock}</div>
    </div>
  `;
}

// Render Alerts Section
function renderAlerts() {
  const today = new Date().toISOString().split("T")[0];
  const expiredItems = products.filter(p => p.expiry < today);
  const lowStockItems = products.filter(p => p.quantity <= 5);

  alertSections.innerHTML = "";

  if (expiredItems.length > 0) {
    const box = document.createElement("div");
    box.className = "bg-red-100 border-l-4 border-red-500 p-4 rounded-xl shadow";
    box.innerHTML = `
      <h3 class="font-bold text-red-700">⚠ Expired (${expiredItems.length})</h3>
      ${expiredItems.map(p => `<p>• ${p.name} - ${p.expiry}</p>`).join("")}
    `;
    alertSections.appendChild(box);
    alertBtn.classList.remove("hidden");
    alertBtn.innerText = `🔺 ${expiredItems.length} Alerts`;
  } else {
    alertBtn.classList.add("hidden");
  }

  if (lowStockItems.length > 0) {
    const box = document.createElement("div");
    box.className = "bg-yellow-100 border-l-4 border-yellow-500 p-4 rounded-xl shadow";
    box.innerHTML = `
      <h3 class="font-bold text-yellow-700">📉 Low Stock (${lowStockItems.length})</h3>
      ${lowStockItems.map(p => `<p>• ${p.name} - ${p.quantity} left</p>`).join("")}
    `;
    alertSections.appendChild(box);
  }
}

// Update UI
function renderUI() {
  renderDashboard();
  renderAlerts();
}

renderUI();


// QR SCANNER SECTION
const qrReader = new Html5Qrcode("qr-reader");

// Start Camera Scan
Html5Qrcode.getCameras().then(devices => {
  if (devices && devices.length) {
    const camId = devices[0].id;
    qrReader.start(
      camId,
      { fps: 10, qrbox: 250 },
      (decodedText) => {
        console.log("QR Code scanned: ", decodedText);
        const parts = decodedText.split("|");
        if (parts.length === 3) {
          document.getElementById("productName").value = parts[0];
          document.getElementById("expiryDate").value = parts[1];
          document.getElementById("quantity").value = parts[2];
          alert("✅ QR Code Detected and Fields Auto-Filled!");
          qrReader.stop(); // Stop after successful scan
        }
      },
      (err) => {
        // console.warn("QR Scan Error", err);
      }
    );
  }
}).catch(err => {
  console.error("Camera error:", err);
});


// Image Upload Scan
document.getElementById("qr-file").addEventListener("change", e => {
  const file = e.target.files[0];
  if (!file) return;
  qrReader.scanFile(file, true)
    .then(decodedText => {
      console.log("QR from image: ", decodedText);
      const parts = decodedText.split("|");
      if (parts.length === 3) {
        document.getElementById("productName").value = parts[0];
        document.getElementById("expiryDate").value = parts[1];
        document.getElementById("quantity").value = parts[2];
        alert("✅ QR Image Decoded and Fields Auto-Filled!");
      }
    })
    .catch(err => {
      alert("❌ Failed to decode image QR.");
      console.error("QR image scan error:", err);
    });
});
