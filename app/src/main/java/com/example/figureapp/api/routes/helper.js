
const jwt = require('jsonwebtoken')
function authenticateToken(req, res, next) {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];
    if (token == null) return res.sendStatus(401);
    jwt.verify(token, process.env.ACCESS_TOKEN_SECRET, (err, user) => {
      if (err) {
        return res.status(403).json({ error: 'Forbidden' });
      }
      req.user = user;
      next();
    });
  }
function parseUserId(req){
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1];
  const data = jwt.decode(token);
  return data.userId;
}
function parseRole(req){
  const authHeader = req.headers['authorization'];
  const token = authHeader && authHeader.split(' ')[1];
  const data = jwt.decode(token);
  return data.role;
}
module.exports={authenticateToken, parseUserId, parseRole};  